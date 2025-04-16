package net.refinedsolution.simulation;

import org.jetbrains.annotations.NotNull;

/**
 * A world in which the simulation takes place.
 * @author Java3east
 */
public interface World {
    /**
     * Creates an empty copy of this world
     * @return a copy of this world
     */
    @NotNull World copy();

    /**
     * Returns the simulator this world belongs to.
     * @return the simulator this world belongs to
     */
    @NotNull Simulator getSimulator();
}
