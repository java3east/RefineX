package net.refinedsolution.context.helix;

import net.refinedsolution.RefineX;
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
import net.refinedsolution.util.Marker;
import net.refinedsolution.util.file.TempFileManager;
import net.refinedsolution.util.issue.TraceEntry;
import org.jetbrains.annotations.NotNull;

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
    public void start(@NotNull Simulator simulator, @NotNull Resource resource) {
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

        runner.getGlobals().set("REFX_CLIENT_ID", clId.lua());
        runner.getGlobals().set("REFX_RESOURCE_PATH", resource.getLocation().getPath());

        runner.loadFile("lib/override.lua");
        runner.loadFile("lib/native.lua");


        TempFileManager.ModifierFunction modifier = (str, file) -> {
            return str;
        };
        String file = resource.getLocation().getPath() + "/Shared/Index.lua";

        TempFileManager.fromFile(file, modifier)
                .ifPresent(runner::loadFile);
        file = resource.getLocation().getPath() + path;
        TempFileManager.fromFile(file, modifier)
                .ifPresent(runner::loadFile);
    }
}
