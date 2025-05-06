package net.refinedsolution.resource;

import net.refinedsolution.lua.LuaInterface;
import net.refinedsolution.simulation.Simulator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResourceManagerImpl implements ResourceManager {
    private final HashMap<Resource, LuaInterface> resources = new HashMap<>();
    private final Simulator simulator;
    private final ResourceLoader resourceLoader;

    /**
     * Creates a new ResourceManagerImpl for the given simulator
     * @param simulator the simulator to create the resource manager for
     * @param resourceLoader the resource loader to use
     */
    public ResourceManagerImpl(@NotNull Simulator simulator, @NotNull ResourceLoader resourceLoader) {
        this.simulator = simulator;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public @NotNull Simulator getSimulator() {
        return simulator;
    }

    @Override
    public void start(@NotNull Resource resource) {
        LuaInterface luaInterface = resourceLoader.start(this.simulator, resource);
        this.resources.put(resource, luaInterface);

    }

    @Override
    public void stop(@NotNull Resource resource) {
        LuaInterface luaInterface = this.resources.get(resource);

    }

    @Override
    public List<Resource> getResources() {
        return new ArrayList<>(resources.keySet());
    }
}
