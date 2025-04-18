package net.refinedsolution.context.refex.natives;

import net.refinedsolution.RefineX;
import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.castable.CDouble;
import net.refinedsolution.lua.castable.CFunction;
import net.refinedsolution.lua.castable.CInt;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.lua.nat.Native;
import net.refinedsolution.simulation.*;
import net.refinedsolution.util.GUID;

import java.nio.file.Path;
import java.util.Arrays;

/**
 * This class contains native functions related to RefineX resource simulation.
 * @author Java3east
 */
public class SIM {
    @Native
    public static CInt CreateSimulation(Runner runner, SimulationContext context, CString name) {
        SimulationImpl sim = new SimulationImpl(context, name.get());
        return new CInt((int) sim.getId());
    }

    @Native
    public static CInt AddSimulatedClient(Runner runner, CInt simulation, CString[] identifiers) {
        if (!GUID.has(simulation.get(), Simulation.class))
            throw new IllegalArgumentException("Simulation does not exist");

        Simulation sim = (Simulation) GUID.identify(simulation.get(), Simulation.class);
        ClientImpl client = new ClientImpl(
                sim.getServer(), Arrays.stream(identifiers).map(CString::get).toArray(String[]::new),
                sim.getContext().getWorld()
        );
        sim.getServer().connect(client);

        return new CInt(client.getClientId());
    }

    @Native
    public static void LoadResource(Runner runner, CInt simulation, CString path) {
        Simulation sim = (Simulation) GUID.identify(simulation.get(), Simulation.class);
        sim.load(Path.of(path.get()));
    }

    @Native
    public static void StartResource(Runner runner, CInt simulation, CString name) {
        Simulation sim = (Simulation) GUID.identify(simulation.get(), Simulation.class);
        sim.start(name.get());
    }

    @Native
    public static void Async(Runner runner, CFunction func) {
        Thread t = new Thread(() -> {
            func.get().invoke();
            RefineX.onThreadFinish(Thread.currentThread());
        });
        RefineX.threadRegister.add(t);
        t.start();
    }

    @Native
    public static void REFX_TICK(Runner runner, CInt simId, CDouble delta) {
        if (!GUID.has(simId.get(), Simulation.class))
            throw new IllegalArgumentException("Simulation does not exist");
        Simulation sim = (Simulation) GUID.identify(simId.get(), Simulation.class);
        sim.tick(delta.get());
    }
}
