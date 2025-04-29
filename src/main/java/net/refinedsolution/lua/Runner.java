package net.refinedsolution.lua;

import net.refinedsolution.simulation.Simulator;
import net.refinedsolution.util.async.ThreadObserver;
import net.refinedsolution.util.guid.GUID;
import net.refinedsolution.util.guid.GUIDHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * A runner can execute Lua code. It also contains a working directory and an uniq identifying id.
 * @author Java3east
 */
public interface Runner extends ThreadObserver, GUIDHolder {

    static Runner empty() {
        return new Runner() {
            @Override
            public @NotNull Globals getGlobals() {
                return JsePlatform.standardGlobals();
            }

            @Override
            public void loadStr(@NotNull String string) { }

            @Override
            public void loadFile(@NotNull String filename) { }

            @Override
            public @NotNull File getLocalDirectory() {
                return new File(System.getProperty("user.dir"));
            }

            @Override
            public @NotNull List<Class<?>> getNamespaces() {
                return List.of();
            }

            @Override
            public @NotNull LuaValue call(@Nullable Object o, @NotNull String mName, @NotNull Varargs varargs) {
                return LuaValue.NIL;
            }

            @Override
            public Optional<Simulator> getSimulator() {
                return Optional.empty();
            }

            @Override
            public void observe(@NotNull Thread thread) { }

            @Override
            public void observe(@NotNull ThreadObserver observer) { }

            @Override
            public void await() { }

            @Override
            public void setGUID(@NotNull GUID guid) { }

            @Override
            public @NotNull GUID getGUID() {
                return new GUID(0L);
            }
        };
    }

    /**
     * Returns the Globals for this runner.
     * @return the globals for this runner
     */
    @NotNull Globals getGlobals();

    /**
     * Loads the given string into the globals.
     * @param string the string to load as script.
     */
    void loadStr(@NotNull String string);

    /**
     * Loads the given file into the globals.
     * @param filename the path to the file to load
     */
    void loadFile(@NotNull String filename);

    /**
     * Returns the current directory of this runner.
     * @return the runners current directory.
     */
    @NotNull File getLocalDirectory();

    /**
     * Returns the list of accepted namespaces for this Runner.
     * @return the accepted namespaces
     */
    @NotNull List<Class<?>> getNamespaces();

    /**
     * Calls the given method for this runner with / without the given object and varargs.
     * @param o the optional object to pass as instance to call on
     * @param mName the method name to call
     * @param varargs the arguments to pass
     * @return the return parameter of the method
     */
    @NotNull LuaValue call(@Nullable Object o, @NotNull String mName, @NotNull Varargs varargs);

    /**
     * Returns the simulator this runner is running in.
     * @return the simulator this runner is running in
     */
    Optional<Simulator> getSimulator();
}
