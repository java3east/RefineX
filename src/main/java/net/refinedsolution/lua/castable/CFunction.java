package net.refinedsolution.lua.castable;

import net.refinedsolution.lua.ACastable;
import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;

@ACastable(isDirect = true)
public class CFunction extends CCastable<LuaFunction> {
    @ACastable.Direct
    public CFunction(@NotNull LuaValue value) {
        super(value);
    }

    @Override
    protected @NotNull LuaFunction from(@NotNull LuaValue val) {
        return val.checkfunction();
    }

    @Override
    @ACastable.Field(name = "value")
    public LuaValue lua() {
        if (isUndefined()) return LuaValue.NIL;
        return get();
    }
}
