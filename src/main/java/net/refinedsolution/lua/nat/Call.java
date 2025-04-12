package net.refinedsolution.lua.nat;

import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.Value;
import net.refinedsolution.util.GUID;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

/**
 * Calls the given native function with the given arguments, for the
 * given runner.
 * @author Java3east
 */
public class Call extends VarArgFunction {
    /**
     * @param varargs (1: runnerId: long, 2: objId: long, name: string, ...: any)
     * @return the return params form the method call
     */
    @Override
    public Varargs invoke(Varargs varargs) {
        long runnerId = varargs.checklong(1);
        long objectId = varargs.checklong(2);
        String mName = varargs.checkjstring(3);
        Varargs params = varargs.subargs(4);

        Runner runner = (Runner) GUID.identify(runnerId, Runner.class);
        Object obj = GUID.has(objectId) ? GUID.identify(objectId) : null;
        return Value.varargs(runner.call(obj, mName, params));
    }
}
