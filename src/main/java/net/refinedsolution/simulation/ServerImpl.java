package net.refinedsolution.simulation;

import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.Value;
import net.refinedsolution.resource.Resource;
import net.refinedsolution.util.GUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.luaj.vm2.LuaValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A simple server implementation
 * @author Java3east
 */
public class ServerImpl implements Server {
    private final long id;
    private final Simulation simulation;
    private int nextClientId = 1;
    private final HashMap<Resource, Runner> runners = new HashMap<>();
    private final World world;

    /**
     * Creates a new server instance
     * @param simulation the simulation this simulated server belongs to
     */
    public ServerImpl(Simulation simulation, World world) {
        this.id = GUID.identify(this);
        this.simulation = simulation;
        this.world = world;
    }

    @Override
    public @NotNull List<Client> getClients() {
        return new ArrayList<>(this.simulation.getClients());
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
    public void addResource(@NotNull Resource resource, @NotNull Runner runner) {
        this.runners.put(resource, runner);
    }

    @Override
    public Runner getRunner(@NotNull Resource resource) {
        return this.runners.get(resource);
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public void dispatchEvent(@NotNull Event event) {
        LuaValue val = Value.of(event);
        for (Runner runner : runners.values()) {
            runner.getGlobals().get("REFX_DISPATCH_EVENT").call(Value.of(val));
        }
    }

    @Override
    public void tick(double delta) {
        for (Runner runner : runners.values()) {
            runner.getGlobals().get("REFX_TICK").call(Value.of(delta));
        }
    }

    @Override
    public long getId() {
        return this.id;
    }
}
