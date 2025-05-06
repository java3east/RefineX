package net.refinedsolution.context.refex.natives;

import net.refinedsolution.lua.LuaInterface;
import net.refinedsolution.lua.castable.CFunction;
import net.refinedsolution.lua.nat.Native;
import net.refinedsolution.simulation.*;

import java.nio.file.Path;
import java.util.Arrays;

/**
 * This class contains native functions related to RefineX resource simulation.
 * @author Java3east
 */
public class SIM {
    @Native
    public static int CreateSimulation(LuaInterface runner, SimulationContext context, String name) {
        SimulationImpl sim = new SimulationImpl(context, name);
        // TODO
        return 0;
    }

    @Native
    public static int AddSimulatedClient(LuaInterface runner, int simulation, String[] identifiers) {
        return 0;
    }

    @Native
    public static void LoadResource(LuaInterface runner, int simulation, String path) {
    }

    @Native
    public static void StartResource(LuaInterface runner, int simulation, String name) {
    }

    @Native
    public static void Async(LuaInterface runner, CFunction func) {
    }

    @Native
    public static void REFX_TICK(LuaInterface runner, int simId, double delta) {
    }
}
