package net.refinedsolution.resource;

import net.refinedsolution.simulation.Simulator;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A resource manager is responsible for managing the resources for
 * a single simulator.
 * @author Java3east
 */
public interface ResourceManager {
    /**
     * Returns teh simulator this resource manager belongs to.
     * @return the simulator
     */
    @NotNull Simulator getSimulator();

    /**
     * Starts the provided resource
     * @param resource the resource to start
     */
    void start(@NotNull Resource resource);

    /**
     * Stops the provided resource
     * @param resource the resource to stop
     */
    void stop(@NotNull Resource resource);

    /**
     * Returns all resources known and managed by this
     * resource manager. This is also the list of resources
     * currently running for the simulator this resource manager
     * belongs to.
     * @return a list of all registered resources
     */
    List<Resource> getResources();
}
