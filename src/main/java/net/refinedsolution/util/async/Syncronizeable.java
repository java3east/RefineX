package net.refinedsolution.util.async;

/**
 * A syncronizable object is an object that can be executed in sync or async.
 */
public interface Syncronizeable {
    /**
     * Executes this syncronizable and waits for it to finish.
     */
    public void sync();

    /**
     * Executes this syncronizable async.
     */
    public void async();

    /**
     * Executes this syncronizable async and calls the given runnable when it is done.
     * @param runnable the runnable to call when the syncronizable is done
     */
    public void async(Runnable runnable);
}
