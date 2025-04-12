package net.refinedsolution.simulation;

/**
 * Simulates a client
 */
public interface Client extends Simulator {
    /**
     * Returns the client id of this simulator.
     * @return the id of this client
     */
    int getClientId();

    /**
     * Returns the server this client is connected to.
     * @return the server this client is connected to.
     */
    Server getServer();

}
