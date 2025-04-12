package net.refinedsolution.context.fivem;

import net.refinedsolution.simulation.Simulator;
import net.refinedsolution.simulation.World;
import org.jetbrains.annotations.NotNull;

public class FivemWorld implements World {
    private Simulator simulator;

    public FivemWorld(Simulator simulator) {
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
        return new FivemWorld(this.simulator);
    }

    @Override
    public @NotNull Simulator getSimulator() {
        return simulator;
    }
}
