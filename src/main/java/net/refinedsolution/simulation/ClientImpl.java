package net.refinedsolution.simulation;

import net.refinedsolution.lua.Runner;
import net.refinedsolution.lua.Value;
import net.refinedsolution.resource.Resource;
import net.refinedsolution.util.GUID;
import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaValue;

import java.util.HashMap;

/**
 * A simple client implementation
 * @author Java3east
 */
public class ClientImpl implements Client {
    private final long id;
    private final int clientId;
    private final Server server;
    private final String[] identifiers;
    private final World world;
    private final HashMap<Resource, Runner> runners = new HashMap<>();

    /**
     * Creates a new client object
     * @param server the server this client should be connected to
     */
    public ClientImpl(Server server, String[] identifiers, World world) {
        this.id = GUID.identify(this);
        this.clientId = server.nextClientId();
        this.server = server;
        this.identifiers = identifiers;
        this.world = world;
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
    public void addResource(@NotNull Resource resource, @NotNull Runner runner) {
        runners.put(resource, runner);
    }

    @Override
    public Runner getRunner(@NotNull Resource resource) {
        return runners.get(resource);
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public boolean dispatchEvent(@NotNull Event event) {
        boolean ok = false;
        for (Runner runner : runners.values()) {
            LuaValue ret = runner.getGlobals().get("REFX_DISPATCH_EVENT").call(Value.of(event));
            if (ret.checkboolean()) {
                ok = true;
            }
        }
        return ok;
    }

    @Override
    public void tick(double delta) {
        for (Runner runner : runners.values()) {
            runner.getGlobals().get("REFX_TICK").call(Value.of(delta));
        }
    }

    @Override
    public @NotNull String getName() {
        return "CLIENT:" + this.getClientId();
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
