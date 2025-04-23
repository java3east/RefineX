package net.refinedsolution.simulation;

import net.refinedsolution.lua.Runner;
import net.refinedsolution.resource.Resource;
import net.refinedsolution.util.Idable;
import org.jetbrains.annotations.NotNull;

/**
 * A Simulator is a part of a simulation and can act as various things (e.g. server)
 * @author Java3east
 */
public interface Simulator extends Idable {
    /**
     * The simulation this simulator belongs to
     * @return the simulation this simulator simulates in.
     */
    @NotNull Simulation getSimulation();

    /**
     * Adds the resource with the given runner to the simulator
     * @param resource the resource to add
     * @param runner the runner of the resource for this simulator
     */
    void addResource(@NotNull Resource resource, @NotNull Runner runner);

    /**
     * Returns the runner for the given resource
     * @param resource the resource to get the runner for
     * @return the runner for the given resource
     */
    Runner getRunner(@NotNull Resource resource);

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
