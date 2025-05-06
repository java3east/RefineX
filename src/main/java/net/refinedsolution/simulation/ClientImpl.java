package net.refinedsolution.simulation;

import net.refinedsolution.context.helix.HelixResourceLoader;
import net.refinedsolution.lua.LuaInterface;
import net.refinedsolution.lua.Value;
import net.refinedsolution.resource.Resource;
import net.refinedsolution.resource.ResourceManager;
import net.refinedsolution.resource.ResourceManagerImpl;
import net.refinedsolution.util.guid.GUID;
import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaValue;

import java.util.HashMap;

/**
 * A simple client implementation
 * @author Java3east
 */
public class ClientImpl implements Client {
    private final int clientId;
    private final Server server;
    private final String[] identifiers;
    private final World world;
    private final HashMap<Resource, LuaInterface> runners = new HashMap<>();

    /**
     * Creates a new client object
     * @param server the server this client should be connected to
     */
    public ClientImpl(Server server, String[] identifiers, World world) {
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
    public @NotNull ResourceManager getResourceManager() {
        return new ResourceManagerImpl(this, new HelixResourceLoader());
    }

    @Override
    public @NotNull Simulation getSimulation() {
        return this.server.getSimulation();
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public boolean dispatchEvent(@NotNull Event event) {
        return false;
    }

    @Override
    public void tick(double delta) {

    }

    @Override
    public @NotNull String[] getIdentifiers() {
        return this.identifiers;
    }
}
