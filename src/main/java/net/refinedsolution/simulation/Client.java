package net.refinedsolution.simulation;

import org.jetbrains.annotations.NotNull;

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
    @NotNull Server getServer();

    /**
     * Returns the identifiers of this client
     * @return the identifiers of this client
     */
    @NotNull String[] getIdentifiers();
}
