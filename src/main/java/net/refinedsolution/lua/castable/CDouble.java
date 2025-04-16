package net.refinedsolution.lua.castable;

import net.refinedsolution.lua.ACastable;
import net.refinedsolution.lua.Value;
import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.*;

/**
 * This class represents a castable integer
 * @author Java3east
 */
@net.refinedsolution.lua.ACastable(isDirect = true, castType = LuaDouble.class)
public class CDouble extends CCastable<Double> {

    static {{
        Value.castables.put(LuaDouble.class, CDouble.class);
    }}

    @net.refinedsolution.lua.ACastable.Direct
    public CDouble(LuaValue value) {
        super(value);
    }

    public CDouble(Double value) {
        super(value);
    }

    @Override
    protected @NotNull Double from(@NotNull LuaValue val) {
        return val.checkdouble();
    }

    @Override
    @ACastable.PackField(name = "value")
    public LuaValue lua() {
        if (isUndefined()) return LuaValue.valueOf(0);
        return LuaValue.valueOf(get());
    }
}
