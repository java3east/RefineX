package net.refinedsolution.lua.castable;

import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;

public class CFunction implements ICastable {
    LuaFunction luaFunction;

    public CFunction(@NotNull LuaValue value) {
        if (!value.isfunction())
            throw new IllegalArgumentException("Value is not a function");
        this.luaFunction = value.checkfunction();
    }

    @Override
    public LuaValue lua() {
        return luaFunction;
    }
}
