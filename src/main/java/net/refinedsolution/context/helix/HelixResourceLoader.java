package net.refinedsolution.context.helix;

import net.refinedsolution.context.helix.natives.EVENT;
import net.refinedsolution.context.refex.natives.REFX;
import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.RunnerImpl;
import net.refinedsolution.lua.castable.CInt;
import net.refinedsolution.resource.Resource;
import net.refinedsolution.resource.ResourceImpl;
import net.refinedsolution.resource.ResourceLoader;
import net.refinedsolution.simulation.Client;
import net.refinedsolution.simulation.Simulator;
import net.refinedsolution.util.file.TempFileManager;
import org.jetbrains.annotations.NotNull;

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

        TempFileManager.fromFile(resource.getLocation().getPath() + "/Shared/Index.lua", (str) -> """
                print("Loading", REFX_RESOURCE_PATH)
                local function split(str, sep)
                    local t = {}
                    for s in str:gmatch("[^"..sep.."]+") do
                        table.insert(t, s)
                    end
                    return t
                end
                local ok, err = pcall(function()\n"""  + str + """
                \nend)
                print("ok", ok, err)
                if not ok then
                    local lines = split(err, "\\n")
                    local info = debug.getinfo(1, "Sl")
                    REFX_ERROR("ERROR", lines[1], "?", {
                        { file = info.short_src, line = info.currentline }
                    })
                end
                """)
                .ifPresent(runner::loadFile);
        TempFileManager.fromFile(resource.getLocation().getPath() + path, (str) -> """
                print("Loading", REFX_RESOURCE_PATH)
                local function split(str, sep)
                    local t = {}
                    for s in str:gmatch("[^"..sep.."]+") do
                        table.insert(t, s)
                    end
                    return t
                end
                local ok, err = pcall(function()\n"""  + str + """
                \nend)
                print("ok", ok, err)
                if not ok then
                    local lines = split(err, "\\n")
                    local info = debug.getinfo(1, "Sl")
                    REFX_ERROR("ERROR", lines[1], "?", {
                        { file = info.short_src, line = info.currentline }
                    })
                end
                """)
                .ifPresent(runner::loadFile);
    }
}
