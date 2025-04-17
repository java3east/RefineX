package net.refinedsolution.context.refex.natives;

import net.refinedsolution.RefineX;
import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.castable.*;
import net.refinedsolution.lua.nat.Native;
import net.refinedsolution.resource.Resource;
import net.refinedsolution.simulation.*;
import net.refinedsolution.util.GUID;
import net.refinedsolution.util.test.MutationManager;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

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
    public static CInt AddSimulatedClient(Runner runner, CInt simulation, CString[] identifiers, CInt mutation) {
        if (!GUID.has(simulation.get(), Simulation.class))
            throw new IllegalArgumentException("Simulation does not exist");

        Simulation sim = (Simulation) GUID.identify(simulation.get(), Simulation.class);
        ClientImpl client = new ClientImpl(
                sim.getServer(), Arrays.stream(identifiers).map(CString::get).toArray(String[]::new),
                sim.getContext().getWorld()
        );
        sim.getServer().connect(client);
        for (Resource res : sim.getResources()) {
            sim.getContext().getResourceLoader().start(client, res, mutation.get());
        }
        return new CInt(client.getClientId());
    }

    @Native
    public static void LoadResource(Runner runner, CInt simulation, CString path) {
        Simulation sim = (Simulation) GUID.identify(simulation.get(), Simulation.class);
        sim.load(Path.of(path.get()));
    }

    @Native
    public static void StartResource(Runner runner, CInt simulation, CString name, CInt mutation) {
        Simulation sim = (Simulation) GUID.identify(simulation.get(), Simulation.class);
        sim.start(name.get(), mutation.get());
    }

    @Native
    public static void Async(Runner runner, CFunction func) {
        Thread t = new Thread(() -> {
            func.get().invoke();
        });
        t.start();
    }

    @Native
    public static void REFX_TICK(Runner runner, CInt simId, CDouble delta) {
        if (!GUID.has(simId.get(), Simulation.class))
            throw new IllegalArgumentException("Simulation does not exist");
        Simulation sim = (Simulation) GUID.identify(simId.get(), Simulation.class);
        sim.tick(delta.get());
    }

    @Native
    public static CBool RefxSimHasErrors(Runner runner, CInt simId) {
        if (!GUID.has(simId.get(), Simulation.class))
            throw new IllegalArgumentException("Simulation does not exist");
        Simulation sim = (Simulation) GUID.identify(simId.get(), Simulation.class);
        return new CBool(sim.hasIssues());
    }

    @Native
    public static void RefxSimPostProcess(Runner runner, CInt simId) {
        if (!GUID.has(simId.get(), Simulation.class))
            throw new IllegalArgumentException("Simulation does not exist");
        Simulation sim = (Simulation) GUID.identify(simId.get(), Simulation.class);
        sim.postProcess();
    }
}
