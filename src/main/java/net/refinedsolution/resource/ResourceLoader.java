package net.refinedsolution.resource;

import net.refinedsolution.simulation.Simulator;
import org.jetbrains.annotations.NotNull;

/**
 * Resource loaders are used to load resource in their context
 * @author Java3east
 */
public interface ResourceLoader {
    /**
     * Loads a resource from the given path
     * @param path the path to the resource
     * @return the resource
     *
     * @throws NullPointerException if the resource was not found
     */
    @NotNull Resource load(@NotNull String path);

    /**
     * Starts the given resource for the given simulator
     * @param simulator the simulator to start the resource for
     * @param resource the resource to start
     */
    void start(@NotNull Simulator simulator, @NotNull Resource resource);
}
