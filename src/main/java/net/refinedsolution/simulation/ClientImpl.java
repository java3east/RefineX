package net.refinedsolution.simulation;

import net.refinedsolution.util.GUID;
import org.jetbrains.annotations.NotNull;

/**
 * A simple client implementation
 * @author Java3east
 */
public class ClientImpl implements Client {
    private final long id;
    private final int clientId;
    private final Server server;
    private final String[] identifiers;

    /**
     * Creates a new client object
     * @param server the server this client should be connected to
     */
    public ClientImpl(Server server, String[] identifiers) {
        this.id = GUID.identify(this);
        this.clientId = server.nextClientId();
        this.server = server;
        this.identifiers = identifiers;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    @Override
    public @NotNull Server getServer() {
        return this.server;
    }

    @Override
    public @NotNull Simulation getSimulation() {
        return this.server.getSimulation();
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public @NotNull String[] getIdentifiers() {
        return this.identifiers;
    }
}
