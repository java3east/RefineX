package net.refinedsolution.context.helix;

import net.refinedsolution.context.refex.natives.REFX;
import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.RunnerImpl;
import net.refinedsolution.resource.Resource;
import net.refinedsolution.resource.ResourceImpl;
import net.refinedsolution.resource.ResourceLoader;
import net.refinedsolution.simulation.Client;
import net.refinedsolution.simulation.Simulator;
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
        runner.addNamespace(REFX.class);
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

        runner.loadFile("lib/override.lua");
        runner.loadFile("lib/native.lua");
        runner.loadFile(resource.getLocation().getPath() + "/Shared/Index.lua");
        runner.loadFile(resource.getLocation().getPath() + path);

        simulator.addResource(resource, runner);
    }
}
