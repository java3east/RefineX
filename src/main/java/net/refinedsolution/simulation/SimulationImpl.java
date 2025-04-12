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
    private final SimulationContext context;

    public SimulationImpl(SimulationContext context) {
        this.id = GUID.identify(this);
        this.context = context;
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
    public SimulationContext getContext() {
        return context;
    }

    @Override
    public long getId() {
        return this.id;
    }
}
