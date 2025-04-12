package net.refinedsolution.lua.castable;

import net.refinedsolution.lua.ACastable;
import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaValue;

/**
 * This class represents a Castable String
 * @author Java3east
 */
@ACastable(isDirect = true)
public class CString extends CCastable<String> {
    @ACastable.Direct
    public CString(LuaValue value) {
        super(value);
    }

    public CString(String value) {
        super(value);
    }

    @Override
    protected @NotNull String from(@NotNull LuaValue val) {
        return val.checkjstring();
    }

    @Override
    @ACastable.PackField(name = "value")
    public LuaValue lua() {
        if (isUndefined()) return LuaValue.NIL;
        return LuaValue.valueOf(get());
    }
}
