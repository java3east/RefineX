package net.refinedsolution.simulation;

import net.refinedsolution.resource.Resource;
import net.refinedsolution.util.GUID;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimulationImpl implements Simulation {
    private final long id;
    private final Server server = new ServerImpl(this);
    private final List<Simulator> clients = new ArrayList<>();

    public SimulationImpl() {
        this.id = GUID.identify(this);
    }

    @Override
    public @NotNull Server getServer() {
        return this.server;
    }

    @Override
    public @NotNull List<Simulator> getClients() {
        return clients;
    }

    @Override
    public @NotNull Optional<Simulator> getClient(int id) {
        return Optional.empty();
    }

    @Override
    public @NotNull List<Resource> getResources() {
        return List.of();
    }

    @Override
    public void load(Path path) {

    }

    @Override
    public long getId() {
        return this.id;
    }
}
