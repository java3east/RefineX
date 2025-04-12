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
    private final Server server;
    private final List<Simulator> clients = new ArrayList<>();
    private final List<Resource> resources = new ArrayList<>();
    private final SimulationContext context;

    public SimulationImpl(SimulationContext context) {
        this.id = GUID.identify(this);
        this.server = new ServerImpl(this, context.getWorld());
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
        Resource resource = this.context.getResourceLoader().load(path.toString());
        this.resources.add(resource);
    }

    @Override
    public void start(String name) {
        Optional<Resource> resource = Optional.empty();
        for (Resource res : this.resources) {
            if (res.getName().equals(name)) {
                resource = Optional.of(res);
                break;
            }
        }
        if (resource.isEmpty())
            throw new IllegalArgumentException("Resource not found: " + name);

        this.context.getResourceLoader().start(this.server, resource.get());
        for (Simulator client : this.clients) {
            this.context.getResourceLoader().start(client, resource.get());
        }
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
