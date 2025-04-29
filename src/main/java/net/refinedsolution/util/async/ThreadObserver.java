package net.refinedsolution.util.async;

/**
 * The thread observer is similar to the parallelizer, but threads are added and not set at once.
 * @author Java3east
 */
public interface ThreadObserver {
    /**
     * Adds a new thread to the list of observed threads.
     * @param thread the thread to add
     */
    public void observe(Thread thread);

    /**
     * Adds a new thread observer to the list of observers.
     * On await of this observer, all the child observers are awaited as well.
     * @param observer the observer to add
     */
    public void observe(ThreadObserver observer);

    /**
     * Waits for all the threads to finish.
     */
    public void await();
}
