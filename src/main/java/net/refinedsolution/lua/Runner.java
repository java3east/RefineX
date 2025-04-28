package net.refinedsolution.lua;

import net.refinedsolution.database.Database;
import net.refinedsolution.simulation.Simulator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * A runner can execute Lua code. It also contains a working directory and an uniq identifying id.
 * @author Java3east
 */
public interface Runner {
    /**
     * Returns the id of this runner.
     * Ids should be unique for each runner.
     * @return the unique id of this runner.
     */
    long getId();

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

    /**
     * Returns the database of the simulation of this runner.
     * @return the database.
     */
    Optional<Database> getDatabase();
}
