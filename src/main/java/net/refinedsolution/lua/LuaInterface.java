package net.refinedsolution.lua;

import net.refinedsolution.simulation.Simulator;
import net.refinedsolution.util.async.ThreadObserver;
import net.refinedsolution.util.guid.GUIDHolder;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * A LuaInterface is responsible for loading and communicating with Lua scripts.
 * @author Java3east
 */
public interface LuaInterface extends ThreadObserver, GUIDHolder {
    /**
     * Loads the given file into the globals.
     * @param filename the path to the file to load
     */
    void loadFile(@NotNull String filename);

    /**
     * Returns the list of accepted namespaces for this Runner.
     * @return the accepted namespaces
     */
    @NotNull List<Class<?>> getNamespaces();

    /**
     * Returns the simulator this runner is running in.
     * @return the simulator this runner is running in
     */
    Optional<Simulator> getSimulator();
}
