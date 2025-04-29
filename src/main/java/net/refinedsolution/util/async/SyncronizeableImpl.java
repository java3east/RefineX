package net.refinedsolution.util.async;

import org.jetbrains.annotations.NotNull;

/**
 * A syncronizable object is an object that can be executed in sync or async.
 * @author Java3east
 */
public class SyncronizeableImpl implements Syncronizeable {
    private final Runnable runnable;

    /**
     * Creates a new syncronizable object with the given runnable.
     * @param runnable the runnable to execute
     */
    public SyncronizeableImpl(@NotNull Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void sync() {
        runnable.run();
    }

    @Override
    public void async() {
        new Thread(runnable).start();
    }

    @Override
    public void async(@NotNull Runnable runnable) {
        new Thread(()->{
            this.runnable.run();
            runnable.run();
        }).start();
    }
}
