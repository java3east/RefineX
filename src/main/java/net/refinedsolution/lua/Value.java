package net.refinedsolution.lua;

import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        else if (object.getClass().isAnnotationPresent(ACastable.class)) {
            ACastable castable = object.getClass().getAnnotation(ACastable.class);
            if (castable.isDirect()) {
                for (Method method : object.getClass().getDeclaredMethods())
                    if (method.isAnnotationPresent(ACastable.PackField.class)) {
                        try {
                            return (LuaValue) method.invoke(object);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                throw new ClassCastException("Castable " + object.getClass().getName() + " is marked direct, but has no direct conversion.");
            }


            LuaTable tbl = new LuaTable();
            for (Method method : object.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(ACastable.PackField.class)) {
                    String name = method.getAnnotation(ACastable.PackField.class).name();
                    try {
                        tbl.set(name, of(method.invoke(object)));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return tbl;
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

    /**
     * Creates a new object of the given class from the given value
     * @param value the lua value to create the table from
     * @param clazz the class to build the object from
     * @return the created object
     *
     * @throws ClassCastException if there is no valid constructor and the given value is not a table
     */
    private static @NotNull Object createObject(@NotNull LuaValue value, Class<?> clazz)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.isAnnotationPresent(ACastable.Direct.class)) {
                return constructor.newInstance(value);
            }
        }

        if (!value.istable()) throw new ClassCastException("Cannot cast " + clazz + " to " + value.getClass());
        LuaTable tbl = value.checktable();

        Object instance = clazz.newInstance();
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(ACastable.Field.class)) {
                String name = method.getAnnotation(ACastable.Field.class).name();
                if (tbl.get(name) != null && !tbl.get(name).equals(LuaValue.NIL)) {
                    Object param = castTo(tbl.get(name), method.getParameterTypes()[0]);
                    method.invoke(instance, param);
                }
            }
        }
        return instance;
    }

    /**
     * Tries to cast the given lua value to the given class.
     * @param value the value to cast
     * @param type the class to cast to
     * @return the object of the requested type
     *
     * @throws ClassCastException if the casting failed
     */
    public static @NotNull Object castTo(@NotNull LuaValue value, @NotNull Class<?> type) {
        if (type.isAnnotationPresent(ACastable.class)) {
            try {
                return createObject(value, type);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new ClassCastException("Unable to cast " + type + "(" + value.typename() + ") to " + value + ": " + e.getMessage());
            }
        }
        else if (type.isInstance(value)) return value;
        else if (type == Integer.class && value.isint()) return value.checkint();
        else if (type == Double.class && value.isnumber()) return value.checkdouble();
        else if (type == Long.class && value.isnumber()) return value.checklong();
        else if (type == Boolean.class && value.isboolean()) return value.checkboolean();
        else if (type == String.class && value.isstring()) return value.checkjstring();

        throw new ClassCastException("Cannot cast " + value + " (" + value.typename() + ") to " + type);
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

    /**
     * Converts the given LuaValue into an object
     * @param value the value to convert
     * @return the resulting object
     *
     * @throws IllegalArgumentException if the object could not be converted
     */
    public static @NotNull Object toObject(@NotNull LuaValue value) {
        if (value.isstring()) return value.checkjstring();
        else if (value.isnumber()) {
            double d = value.checkdouble();
            if (Math.floor(d) == d) return (long) d;
            return d;
        }
        else if (value.isboolean()) return value.checkboolean();
        throw new IllegalArgumentException("Cannot convert " + value + " (" + value.type() + ") to an object.");
    }

    /**
     * Converts the given Method into a lua vararg function.
     * @param method the method to convert
     * @return the created function
     */
    public static VarArgFunction luaFunction(Method method) {
        return new VarArgFunction() {
            @Override
            public Varargs invoke(Varargs varargs) {
                Object[] params = new Object[varargs.narg()];
                for (int i = 0; i < varargs.narg(); i++)
                    params[i] = toObject(varargs.arg(i + 1));
                try {
                    Object ret = method.invoke(null, params);
                    return varargs(ret);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
