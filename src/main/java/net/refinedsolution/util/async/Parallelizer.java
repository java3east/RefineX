package net.refinedsolution.util.async;

import org.jetbrains.annotations.NotNull;

/**
 * Allows to organize multiple threads in parallel and wait for them to finish.
 * @author Java3east
 */
public class Parallelizer {
    private final Runnable[] runnable;

    /**
     * Creates a new Parallelizer with the given runnable.
     * @param runnable the runnable to execute in parallel
     */
    public Parallelizer(@NotNull final Runnable ...runnable) {
        this.runnable = runnable;
    }

    /**
     * Returns a syncronizeable object that can be used to execute the given runnable in sync or async.
     */
    public Syncronizeable start() {
        return new SyncronizeableImpl(() -> {
            Thread[] threads = new Thread[runnable.length];
            for (int i = 0; i < runnable.length; i++) {
                threads[i] = new Thread(runnable[i]);
                threads[i].start();
            }
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (Exception ignored) { }
            }
        });
    }
}
