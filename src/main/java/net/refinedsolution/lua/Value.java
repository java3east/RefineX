package net.refinedsolution.lua;

import net.refinedsolution.lua.castable.CFunction;
import net.refinedsolution.lua.castable.ICastable;
import net.refinedsolution.util.utils.ReflectionUtils;
import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.lang.reflect.*;
import java.util.*;

/**
 * Lua value converter.
 * @author Java3east
 */
public class Value {
    /**
     * Converts an object into a LuaValue
     * @param object the object to convert
     * @return the lua value
     */
    public static @NotNull LuaValue of(@NotNull Object object) {
        if (object.getClass().isArray()) {
            LuaTable arr = new LuaTable();
            for (int i = 0; i < Array.getLength(object); i++) {
                arr.set(i + 1, of(Array.get(object, i)));
            }
            return arr;
        }
        else if (object instanceof LuaValue luaValue) return luaValue;
        else if (object instanceof Integer integer) return LuaValue.valueOf(integer);
        else if (object instanceof Long longObject) return LuaValue.valueOf(longObject);
        else if (object instanceof Double doubleObject) return LuaValue.valueOf(doubleObject);
        else if (object instanceof Float floatObject) return LuaValue.valueOf(floatObject);
        else if (object instanceof Boolean booleanObject) return LuaValue.valueOf(booleanObject);
        else if (object instanceof String stringObject) return LuaValue.valueOf(stringObject);
        else if (object instanceof Map<?, ?> map) {
            LuaTable table = new LuaTable();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                table.set(of(entry.getKey()), of(entry.getValue()));
            }
            return table;
        }
        else if (object instanceof ICastable castable) {
            return castable.lua();
        }
        return LuaValue.NIL;
    }

    /**
     * Checks if the given lua value is an array
     * @param value the value to check
     * @return true if the given value is an array
     */
    public static boolean isArray(LuaValue value) {
        if (!value.istable()) return false;
        LuaTable table = new LuaTable();
        for (LuaValue key : table.keys()) {
            if (!key.isint()) return false;
            if (key.checkint() < 1) return false;
        }
        return true;
    }

    /**
     * Converts the given Lua table into an array of lua values
     * @param value the table
     * @return the array of lua values
     */
    public static @NotNull LuaValue[] toArray(@NotNull LuaTable value) {
        if (!isArray(value)) return new LuaValue[0];
        LuaTable tblArr = value.checktable();

        int length = 0;
        for (LuaValue key : tblArr.keys())
            if (key.checkint() > length) length = key.checkint();

        LuaValue[] values = new LuaValue[length];
        for (LuaValue k : tblArr.keys()) {
            values[k.checkint() - 1] = tblArr.get(k);
        }

        return values;
    }

    private static interface ValueMappingFunction {
        @NotNull Object map(@NotNull LuaValue value);
    }

    private static final HashMap<String, ValueMappingFunction> valueMappings = new HashMap<>() {{
        put(Integer.class.getName(), LuaValue::checkint);
        put(String.class.getName(), LuaValue::checkjstring);
        put(Boolean.class.getName(), LuaValue::checkboolean);
        put(Double.class.getName(), LuaValue::checkdouble);
        put(Long.class.getName(), LuaValue::checklong);
        put(Float.class.getName(), (value)->(float) value.checkdouble());
        put(Byte.class.getName(), (value)->(byte) value.checkint());
        put(Short.class.getName(), (value)->(short) value.checkint());
        put(Character.class.getName(), (value)->(char) value.checkint());
        put(CFunction.class.getName(), CFunction::new);
        put("int", LuaValue::checkint);
        put("boolean", LuaValue::checkboolean);
        put("double", LuaValue::checkdouble);
        put("long", LuaValue::checklong);
        put("float", (value)->(float) value.checkdouble());
        put("byte", (value)->(byte) value.checkint());
        put("short", (value)->(short) value.checkint());
        put("char", (value)->(char) value.checkint());
    }};

    private static @NotNull Object createArrayFrom(Class<?> clazz, LuaValue value) {
        LuaValue[] values = toArray(value.checktable());
        Object[] arr = (Object[]) Array.newInstance(clazz, values.length);
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) continue;
            arr[i] = createFrom(clazz, values[i]);
        }
        return arr;
    }

    private static @NotNull Object createFromTable(Class<?> clazz, LuaTable tbl) {
        Object obj = ReflectionUtils.createInstance(clazz);
        for (LuaValue key : tbl.keys()) {
            if (!key.isstring()) continue;
            String name = key.checkjstring();
            Class<?> fieldType = ReflectionUtils.getFieldType(clazz, name);
            Object value = createFrom(fieldType, tbl.get(key));
            ReflectionUtils.set(obj, name, value);
        }
        return obj;
    }

    public static @NotNull Object createFrom(Class<?> clazz, LuaValue value) {
        if (valueMappings.containsKey(clazz.getName()))
            return valueMappings.get(clazz.getName()).map(value);

        if (clazz.isArray())
            return createArrayFrom(clazz.getComponentType(), value);

        if (!value.istable())
            throw new ClassCastException("Failed to cast " + value + " to " + clazz + ": Value is not a table");

        LuaTable tbl = value.checktable();
        return createFromTable(clazz, tbl);
    }

    /**
     * Converts the given object array into a vararg
     * @param objects the objects to pack into the varargs
     * @return the created varargs
     */
    public static @NotNull Varargs varargs(@NotNull Object ...objects) {
        List<LuaValue> values = new ArrayList<>();
        for (Object object : objects) values.add(of(object));
        return LuaValue.varargsOf(values.toArray(new LuaValue[0]));
    }
}
