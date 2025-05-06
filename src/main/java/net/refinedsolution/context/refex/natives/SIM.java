package net.refinedsolution.context.refex.natives;

import net.refinedsolution.lua.LuaInterface;
import net.refinedsolution.lua.castable.CDouble;
import net.refinedsolution.lua.castable.CFunction;
import net.refinedsolution.lua.castable.CInt;
import net.refinedsolution.lua.castable.CString;
import net.refinedsolution.lua.nat.Native;
import net.refinedsolution.simulation.*;
import net.refinedsolution.util.guid.GUID;

import java.nio.file.Path;
import java.util.Arrays;

/**
 * This class contains native functions related to RefineX resource simulation.
 * @author Java3east
 */
public class SIM {
    @Native
    public static CInt CreateSimulation(LuaInterface runner, SimulationContext context, CString name) {
        SimulationImpl sim = new SimulationImpl(context, name.get());
        // TODO
        return new CInt((int) 0);
    }

    @Native
    public static CInt AddSimulatedClient(LuaInterface runner, CInt simulation, CString[] identifiers) {
        return new CInt(0);
    }

    @Native
    public static void LoadResource(LuaInterface runner, CInt simulation, CString path) {
    }

    @Native
    public static void StartResource(LuaInterface runner, CInt simulation, CString name) {
    }

    @Native
    public static void Async(LuaInterface runner, CFunction func) {
    }

    @Native
    public static void REFX_TICK(LuaInterface runner, CInt simId, CDouble delta) {
    }
}
