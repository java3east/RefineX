package net.refinedsolution.context.helix.natives;

import net.refinedsolution.lua.LuaInterface;
import net.refinedsolution.lua.nat.Native;

/**
 * Events can be dispatched on simulators.
 * @author Java3east
 */
public class EVENT {
    @Native
    public static void REFX_CALL_REMOTE(LuaInterface runner, String name, int source, int target) {
        // TODO
    }

    @Native
    public static void REFX_CALL_LOCAL(LuaInterface runner, String name) {
        // TODO
    }
}
