package net.refinedsolution.util.async;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The thread observer is similar to the parallelizer, but threads are added and not set at once.
 * This is the implementation of the {@link ThreadObserver} interface.
 * @author Java3east
 */
public class ThreadObserverImpl implements ThreadObserver {
    private final List<ThreadObserver> observers = new ArrayList<>();
    private final List<Thread> threads = new ArrayList<>();

    @Override
    public void observe(@NotNull Thread thread) {
        threads.add(thread);
    }

    @Override
    public void observe(@NotNull ThreadObserver observer) {
        this.observers.add(observer);
    }


    public void await() {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) { }
        }
        for (ThreadObserver observer : observers) {
            observer.await();
        }
    }
}
