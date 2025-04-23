package net.refinedsolution.simulation;

import net.refinedsolution.database.Database;
import net.refinedsolution.database.DatabaseImpl;
import net.refinedsolution.resource.Resource;
import net.refinedsolution.util.GUID;
import net.refinedsolution.util.issue.Issue;
import net.refinedsolution.util.issue.IssueLog;
import net.refinedsolution.util.issue.IssueLogger;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class SimulationImpl implements Simulation, IssueLog, IssueLogger {
    private final long id;
    private final Server server;
    private final List<Client> clients = new ArrayList<>();
    private final List<Resource> resources = new ArrayList<>();
    private final SimulationContext context;
    private final String name;
    private final List<Issue> issues = new ArrayList<>();
    private final Database database = new DatabaseImpl();

    public SimulationImpl(SimulationContext context, String name) {
        this.id = GUID.identify(this);
        this.server = new ServerImpl(this, context.getWorld());
        this.context = context;
        this.name = name;
    }

    public SimulationImpl(SimulationContext context) {
        this(context, "Simulation");
    }

    @Override
    public @NotNull Server getServer() {
        return this.server;
    }

    @Override
    public List<Client> getClients() {
        return clients;
    }

    @Override
    public @NotNull Optional<Simulator> getClient(int id) {
        for (Client client : this.clients) {
            if (client.getClientId() == id) {
                return Optional.of(client);
            }
        }
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
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public @NotNull Issue[] getIssues() {
        return this.issues.toArray(new Issue[0]);
    }

    @Override
    public @NotNull Stream<Issue> stream() {
        return this.issues.stream();
    }

    @Override
    public void log(@NotNull Issue issue) {
        this.issues.add(issue);
    }

    @Override
    public void tick(double delta) {
        server.tick(delta);
        for (Client client : this.clients) {
            client.tick(delta);
        }
    }

    @Override
    public @NotNull Database getDatabase() {
        return this.database;
    }
}
