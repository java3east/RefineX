package net.refinedsolution.context.helix;

import net.refinedsolution.context.helix.natives.EVENT;
import net.refinedsolution.context.refex.natives.REFX;
import net.refinedsolution.context.refex.natives.TEST;
import net.refinedsolution.lua.RunnerImpl;
import net.refinedsolution.lua.castable.CInt;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.resource.Resource;
import net.refinedsolution.resource.ResourceImpl;
import net.refinedsolution.resource.ResourceLoader;
import net.refinedsolution.simulation.Client;
import net.refinedsolution.simulation.Simulator;
import net.refinedsolution.util.test.Marker;
import net.refinedsolution.util.file.TempFileManager;
import net.refinedsolution.util.issue.TraceEntry;
import net.refinedsolution.util.test.Mutation;
import net.refinedsolution.util.test.MutationManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Loads a HELIX resource.
 * @author Java3east
 */
public class HelixResourceLoader implements ResourceLoader {
    @Override
    public @NotNull Resource load(@NotNull String path) {
        HelixManifest manifest = new HelixManifest();
        manifest.load(path);
        if (!manifest.verify())
            throw new IllegalStateException("Manifest is not valid");
        return new ResourceImpl(path, manifest);
    }

    @Override
    public void start(@NotNull Simulator simulator, @NotNull Resource resource, int mutation) {
        RunnerImpl runner = new RunnerImpl(simulator);
        simulator.addResource(resource, runner);
        runner.addNamespace(REFX.class);
        runner.addNamespace(EVENT.class);
        runner.addNamespace(TEST.class);
        String[] libraries;
        boolean isClient = false;
        if (simulator instanceof Client) {
            libraries = simulator.getSimulation().getContext().getClientLibraries();
            isClient = true;
        } else {
            libraries = simulator.getSimulation().getContext().getServerLibraries();
        }

        for (String library : libraries) {
            runner.loadFile(library);
        }


        String path = "/Server/Index.lua";
        if (isClient) path = "/Client/Index.lua";

        CInt clId = new CInt(-1);
        if (simulator instanceof Client client) {
            clId = new CInt(client.getClientId());
        }

        runner.getGlobals().set("REFX_SIM_ID", new CInt((int)simulator.getSimulation().getId()).lua());
        runner.getGlobals().set("REFX_CLIENT_ID", clId.lua());
        runner.getGlobals().set("REFX_RESOURCE_PATH", resource.getLocation().getPath());

        runner.loadFile("lib/override.lua");
        runner.loadFile("lib/native.lua");

        TempFileManager.ModifierFunction modifier = (str, file) -> {
            Pattern pattern = Pattern.compile("(function\\s+\\w*\\s*\\([^)]*\\s*(?:,\\s*\\.\\.\\.)?\\))");
            Matcher matcher = pattern.matcher(str);
            StringBuilder modifiedContent = new StringBuilder();

            String[] lines = str.split("\n");
            int[] lineOffsets = new int[lines.length];
            int currentOffset = 0;
            for (int i = 0; i < lines.length; i++) {
                lineOffsets[i] = currentOffset;
                currentOffset += lines[i].length() + 1;
            }

            while (matcher.find()) {
                int position = matcher.start();
                int lineNumber = -1;
                for (int i = 0; i < lineOffsets.length; i++) {
                    if (position < lineOffsets[i]) {
                        lineNumber = i;
                        break;
                    }
                }
                if (lineNumber == -1) {
                    lineNumber = lineOffsets.length;
                }

                String markerName = "MARKER" + new Random().nextInt(999999999);
                Marker m = new Marker(markerName, matcher.group(1), new TraceEntry().setFile(new CString(file))
                        .setLine(new CInt(lineNumber)), simulator);
                simulator.getSimulation().registerMarker(m);
                String marker = String.format(" RefxTriggerMarker(REFX_SIM_ID, '%s')", markerName);
                matcher.appendReplacement(modifiedContent, matcher.group(1) + marker);
            }
            matcher.appendTail(modifiedContent);
            str = modifiedContent.toString();

            str = "local function split(str, sep) local t = {} for s in str:gmatch(\"[^\"..sep..\"]+\") do table.insert(t, s) end return t end local ok, err = pcall(function()" +
                    str.replaceFirst("\n", "") + """
                end) if not ok then local lines = split(err, "\\n") local info = debug.getinfo(1, "Sl") REFX_ERROR("ERROR", lines[1], "?", { { file = info.short_src, line = info.currentline } }) end
                """;

            return str;
        };
        String file = resource.getLocation().getPath() + "/Shared/Index.lua";

        String finalPath = path;
        HashMap<String, String> files = TempFileManager.temp(new ArrayList<>() {{
            add(resource.getLocation().getPath() + "/Shared/Index.lua");
            add(resource.getLocation().getPath() + finalPath);
        }}, modifier);
        MutationManager manager = new MutationManager(files);
        Mutation mutationObj = manager.getMutation(mutation);
        for (String fileName : mutationObj.files().keySet()) {
            String content = mutationObj.files().get(fileName);
            try {
                FileWriter writer = new FileWriter(System.getProperty("user.dir") + fileName);
                writer.write(content);
                writer.close();
                runner.loadFile(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
