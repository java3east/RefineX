package net.refinedsolution.refex.natives;

import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.castable.CFunction;
import net.refinedsolution.lua.castable.CInt;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.lua.nat.Native;
import net.refinedsolution.simulation.*;
import net.refinedsolution.util.GUID;

import java.util.Arrays;

/**
 * This class contains native functions related to RefineX resource simulation.
 * @author Java3east
 */
public class SIM {
    @Native
    public static CInt CreateSimulation(Runner runner, SimulationContext context) {
        SimulationImpl sim = new SimulationImpl(context);
        return new CInt((int) sim.getId());
    }

    @Native
    public static CInt AddSimulatedClient(Runner runner, CInt simulation, CString[] identifiers) {
        if (!GUID.has(simulation.get(), Simulation.class))
            throw new IllegalArgumentException("Simulation does not exist");

        Simulation sim = (Simulation) GUID.identify(simulation.get(), Simulation.class);
        ClientImpl client = new ClientImpl(sim.getServer(), Arrays.stream(identifiers).map(CString::get).toArray(String[]::new));
        sim.getServer().connect(client);

        return new CInt(client.getClientId());
    }

    @Native
    public static void Async(Runner runner, CFunction func) {
        new Thread(() -> {
            func.get().invoke();
        }).start();
    }
}
