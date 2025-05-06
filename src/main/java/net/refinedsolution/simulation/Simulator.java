package net.refinedsolution.simulation;

import net.refinedsolution.resource.ResourceManager;
import org.jetbrains.annotations.NotNull;

/**
 * A Simulator is a part of a simulation and can act as various things (e.g. server)
 * @author Java3east
 */
public interface Simulator {
    /**
     * Returns the resource manager responsible for managing the
     * resources of this simulator
     * @return the resource manager
     */
    @NotNull ResourceManager getResourceManager();

    /**
     * The simulation this simulator belongs to
     * @return the simulation this simulator simulates in.
     */
    @NotNull Simulation getSimulation();

    /**
     * Returns the world of this simulator
     * @return the world of this simulator
     */
    World getWorld();

    /**
     * Sends this event to all Runners of this simulator
     * @param event the event to send
     * @return true if a handler was found
     */
    boolean dispatchEvent(@NotNull Event event);

    /**
     * Performs a tick for this simulator
     * @param delta the delta time since the last tick
     */
    void tick(double delta);
}
