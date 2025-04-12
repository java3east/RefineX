package net.refinedsolution.simulation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Server simulator.
 * @author Java3east
 */
public interface Server extends Simulator {
    /**
     * Returns the list of all connected clients.
     * @return all connected clients
     */
    @NotNull List<Client> getClients();

    /**
     * Returns the client with the given client id.
     * @param id the id of the client to get
     * @return the client with the given client id
     */
    @Nullable Client getClient(int id);

    /**
     * Returns the next client id
     * @return the next client id
     */
    int nextClientId();

    /**
     * Connects the given client to this server.
     * This will also set the clients id.
     * @param client the client to connect.
     */
    void connect(@NotNull Client client);
}
