package net.refinedsolution.simulation;

import net.refinedsolution.util.GUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple server implementation
 * @author Java3east
 */
public class ServerImpl implements Server {
    private final long id;
    private final Simulation simulation;
    private int nextClientId = 1;

    /**
     * Creates a new server instance
     * @param simulation the simulation this simulated server belongs to
     */
    public ServerImpl(Simulation simulation) {
        this.id = GUID.identify(this);
        this.simulation = simulation;
    }

    @Override
    public @NotNull List<Client> getClients() {
        List<Client> clients = new ArrayList<>();
        this.simulation.getClients().forEach(c -> clients.add((Client) c));
        return clients;
    }

    @Override
    public @Nullable Client getClient(int id) {
        for (Client c : getClients())
            if (c.getId() == id) return c;
        return null;
    }

    @Override
    public int nextClientId() {
        return this.nextClientId++;
    }

    @Override
    public void connect(@NotNull Client client) {
        this.simulation.getClients().add(client);
    }

    @Override
    public @NotNull Simulation getSimulation() {
        return this.simulation;
    }

    @Override
    public long getId() {
        return this.id;
    }
}
