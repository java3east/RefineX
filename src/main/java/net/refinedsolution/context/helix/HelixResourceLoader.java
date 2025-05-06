package net.refinedsolution.context.helix;

import net.refinedsolution.context.helix.natives.EVENT;
import net.refinedsolution.context.refex.natives.REFX;
import net.refinedsolution.context.refex.natives.TEST;
import net.refinedsolution.lua.LuaInterface;
import net.refinedsolution.lua.LuaInterfaceImpl;
import net.refinedsolution.resource.Resource;
import net.refinedsolution.resource.ResourceImpl;
import net.refinedsolution.resource.ResourceLoader;
import net.refinedsolution.simulation.Client;
import net.refinedsolution.simulation.Simulator;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull LuaInterface start(@NotNull Simulator simulator, @NotNull Resource resource) {
        LuaInterfaceImpl luaInterface = new LuaInterfaceImpl(simulator);
        luaInterface.addNamespace(REFX.class);
        luaInterface.addNamespace(EVENT.class);
        luaInterface.addNamespace(TEST.class);
        String[] libraries;
        boolean isClient = false;
        if (simulator instanceof Client) {
            libraries = simulator.getSimulation().getContext().getClientLibraries();
            isClient = true;
        } else {
            libraries = simulator.getSimulation().getContext().getServerLibraries();
        }

        for (String library : libraries) {
            luaInterface.loadFile(library);
        }


        String path = "/Server/Index.lua";
        if (isClient) path = "/Client/Index.lua";

        luaInterface.loadFile("lib/override.lua");
        luaInterface.loadFile("lib/native.lua");

        String file = resource.getLocation().getPath() + "/Shared/Index.lua";
        luaInterface.loadFile(file);
        luaInterface.loadFile(resource.getLocation().getPath() + path);
        return luaInterface;
    }
}
