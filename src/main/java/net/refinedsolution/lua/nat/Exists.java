package net.refinedsolution.lua.nat;

import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.Value;
import net.refinedsolution.util.guid.GUID;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * Lua function to check if a given function is
 * registered for the given Runner.
 * @author Java3east
 */
public class Exists extends VarArgFunction {
    @Override
    public Varargs invoke(Varargs varargs) {
        if (varargs.narg() != 2)
            throw new IllegalArgumentException("expected 2 arguments to 'NativeExists' but got " + varargs.narg());

        GUID guid = (GUID) Value.castTo(varargs.arg(1), GUID.class);
        Runner runner = (Runner) GUID.get(guid, Runner.class);
        String natName = varargs.checkjstring(2);
        return Value.varargs(NativeReference.find(runner, natName));
    }
}
