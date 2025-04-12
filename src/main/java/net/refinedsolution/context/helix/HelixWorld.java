package net.refinedsolution.context.helix;

import net.refinedsolution.simulation.Simulator;
import net.refinedsolution.simulation.World;
import org.jetbrains.annotations.NotNull;

public class HelixWorld implements World {
    private Simulator simulator;

    public HelixWorld(Simulator simulator) {
        this.simulator = simulator;
    }

    /**
     * Sets the simulator this world belongs to.
     * @param simulator the simulator this world belongs to
     */
    public void setSimulator(Simulator simulator) {
        this.simulator = simulator;
    }

    @Override
    public @NotNull World copy() {
        return new HelixWorld(this.simulator);
    }

    @Override
    public @NotNull Simulator getSimulator() {
        return simulator;
    }
}
