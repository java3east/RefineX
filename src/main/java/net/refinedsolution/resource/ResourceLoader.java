package net.refinedsolution.resource;

import net.refinedsolution.simulation.Simulator;
import net.refinedsolution.util.test.MutationManager;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

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
     * @param mutation the mutation to start the resource with
     */
    void start(@NotNull Simulator simulator, @NotNull Resource resource, int mutation);
}
