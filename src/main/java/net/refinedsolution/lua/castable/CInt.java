package net.refinedsolution.lua.castable;

import net.refinedsolution.lua.ACastable;
import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaValue;

/**
 * This class represents a castable integer
 * @author Java3east
 */
@net.refinedsolution.lua.ACastable(isDirect = true)
public class CInt extends CCastable<Integer> {

    @net.refinedsolution.lua.ACastable.Direct
    public CInt(LuaValue value) {
        super(value);
    }

    public CInt(Integer value) {
        super(value);
    }

    @Override
    protected @NotNull Integer from(@NotNull LuaValue val) {
        return val.checkint();
    }

    @Override
    @ACastable.PackField(name = "value")
    public LuaValue lua() {
        if (isUndefined()) return LuaValue.valueOf(0);
        return LuaValue.valueOf(get());
    }
}
