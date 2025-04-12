package net.refinedsolution.lua.castable;

import net.refinedsolution.lua.ACastable;
import net.refinedsolution.lua.Value;
import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.ArrayList;

@ACastable
public class CList extends CCastable<ArrayList<LuaValue>> {

    @ACastable.Direct
    public CList(@NotNull LuaValue value) {
        super(value);
    }

    public CList(@NotNull ArrayList<LuaValue> list) {
        super(list);
    }

    /**
     * Returns the element at the given index casted to the given type.
     * @param index the index to get the element at
     * @param type the type to cast it to
     * @return the object
     */
    public Object get(int index, Class<?> type) {
        LuaValue v = get().get(index);
        return Value.castTo(v, type);
    }

    /**
     * Adds the given value to this list
     * @param o the object to add
     */
    public void add(Object o) {
        get().add(Value.of(o));
    }

    @Override
    protected @NotNull ArrayList<LuaValue> from(@NotNull LuaValue val) {
        if (!(val instanceof LuaTable t))
            throw new IllegalArgumentException("LuaValue must be a table");
        ArrayList<LuaValue> list = new ArrayList<>();
        for (LuaValue key : t.keys()) {
            list.add(t.get(key));
        }
        return list;
    }

    @Override
    @ACastable.PackField(name = "value")
    public LuaValue lua() {
        if (isUndefined()) return LuaValue.NIL;
        LuaTable tbl = new LuaTable();
        for (int i = 0; i < get().size(); i++) {
            tbl.set(i + 1, get().get(i));
        }
        return tbl;
    }
}
