package net.refinedsolution.lua.castable;

import net.refinedsolution.lua.ACastable;
import net.refinedsolution.lua.Value;
import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaValue;

@ACastable(isDirect = true, castType = LuaBoolean.class)
public class CBool extends CCastable<Boolean> {

    static {{
        Value.castables.put(LuaBoolean.class, CBool.class);
    }}

    @ACastable.Direct
    public CBool(@NotNull LuaValue value) {
        super(value);
    }

    public CBool(boolean value) {
        super(value);
    }

    @Override
    protected @NotNull Boolean from(@NotNull LuaValue val) {
        return val.checkboolean();
    }

    @Override
    @ACastable.PackField(name = "value")
    public LuaValue lua() {
        if (isUndefined()) return LuaValue.valueOf(false);
        else return LuaValue.valueOf(get());
    }
}
