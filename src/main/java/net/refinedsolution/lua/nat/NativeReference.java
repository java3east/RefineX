package net.refinedsolution.lua.nat;

import net.refinedsolution.lua.LuaInterface;

import net.refinedsolution.lua.Value;
import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class is used to call native functions.
 * @author Java3east
 */
public class NativeReference {
    /**
     * Checks if the given class contains a Native function with the given name
     * @param runner the runner to find the native for
     * @param name the name of the function to find
     * @return true if the function exists
     */
    public static boolean find(@NotNull LuaInterface runner, @NotNull String name) {
        for (Class<?> clazz : runner.getNamespaces()) {
            for (Method method : clazz.getMethods()) {
                if (method.getName().equals(name) && method.isAnnotationPresent(Native.class))
                    return true;
            }
        }
        return false;
    }

    private static Object[] cast(LuaValue[] lparams, Class<?>[] types) {
        Object[] results = new Object[types.length];
        for (int i = 1; i < types.length; i++) {
            LuaValue lparam = LuaValue.NIL;
            if (lparams.length > i - 1) {
                lparam = lparams[i - 1];
            }
            Object o = Value.createFrom(types[1], lparam);
            results[i] = o;
        }
        return results;
    }

    private static @NotNull LuaValue call(@NotNull Method method, @NotNull LuaInterface runner, @NotNull LuaValue[] args)
            throws InvocationTargetException, IllegalAccessException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parameters = cast(args, parameterTypes);
        parameters[0] = runner;

        Object o = method.invoke(null, parameters);
        if (o == null) return LuaValue.NIL;
        return Value.of(o);
    }

    /**
     * Calls a function with the given name for the given runner with the given arguments
     * @param runner the runner to call with
     * @param name the name of the function to call
     * @param args the arguments to use
     * @return the Return values
     */
    public static @NotNull LuaValue call(@NotNull LuaInterface runner, @NotNull String name, @NotNull LuaValue[] args) {
        for (Class<?> namespace : runner.getNamespaces()) {
            for (Method method : namespace.getMethods()) {
                if (method.getName().equals(name) && method.isAnnotationPresent(Native.class)) {
                    try {
                        return call(method, runner, args);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        throw new IllegalArgumentException("Could not find method: " + name);
    }
}
