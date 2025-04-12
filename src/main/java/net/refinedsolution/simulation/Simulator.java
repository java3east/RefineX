package net.refinedsolution.simulation;

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
}
