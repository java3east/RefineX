package net.refinedsolution.context.refex.natives;

import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.castable.CInt;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.lua.nat.Native;
import net.refinedsolution.simulation.Simulation;
import net.refinedsolution.util.GUID;

/**
 * Contains functions for testing purposes.
 * @author Java3east
 */
public class TEST {
    @Native
    public static void RefxTriggerMarker(Runner runner, CInt simId, CString name) {
        if (!GUID.has(simId.get(), Simulation.class))
            throw new IllegalArgumentException("Simulation does not exist");
        Simulation sim = (Simulation) GUID.identify(simId.get(), Simulation.class);
        sim.confirmMarker(name.get());
    }
}
