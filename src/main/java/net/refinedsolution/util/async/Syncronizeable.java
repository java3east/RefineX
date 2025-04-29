package net.refinedsolution.util.async;

import org.jetbrains.annotations.NotNull;

/**
 * A syncronizable object is an object that can be executed in sync or async.
 * @author Java3east
 */
public interface Syncronizeable {
    /**
     * Executes this syncronizable and waits for it to finish.
     */
    void sync();

    /**
     * Executes this syncronizable async.
     */
    void async();

    /**
     * Executes this syncronizable async and calls the given runnable when it is done.
     * @param runnable the runnable to call when the syncronizable is done
     */
    void async(@NotNull Runnable runnable);
}
