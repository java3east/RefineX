package net.refinedsolution.simulation;

import net.refinedsolution.context.helix.HelixResourceLoader;
import net.refinedsolution.lua.LuaInterface;
import net.refinedsolution.lua.Value;
import net.refinedsolution.resource.Resource;
import net.refinedsolution.resource.ResourceManager;
import net.refinedsolution.resource.ResourceManagerImpl;
import net.refinedsolution.util.guid.GUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.luaj.vm2.LuaValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple server implementation
 * @author Java3east
 */
public class ServerImpl implements Server {
    private final Simulation simulation;
    private int nextClientId = 1;
    private final HashMap<Resource, LuaInterface> runners = new HashMap<>();
    private final World world;

    /**
     * Creates a new server instance
     * @param simulation the simulation this simulated server belongs to
     */
    public ServerImpl(Simulation simulation, World world) {
        this.simulation = simulation;
        this.world = world;
    }

    @Override
    public @NotNull List<Client> getClients() {
        return new ArrayList<>(this.simulation.getClients());
    }

    @Override
    public @Nullable Client getClient(int id) {
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
    public @NotNull ResourceManager getResourceManager() {
        return new ResourceManagerImpl(this, new HelixResourceLoader());
    }

    @Override
    public @NotNull Simulation getSimulation() {
        return this.simulation;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public boolean dispatchEvent(@NotNull Event event) {
        return false;
    }

    @Override
    public void tick(double delta) {
    }
}
