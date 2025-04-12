package net.refinedsolution.simulation;

import net.refinedsolution.resource.Resource;
import net.refinedsolution.util.Idable;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * A simulation of a client - server environment.
 * @author Java3east
 */
public interface Simulation extends Idable {
    /**
     * Returns the server of this environment
     * @return the server of this environment
     */
    @NotNull Server getServer();

    /**
     * Returns all the clients of this simulation
     * @return the clients of this simulation
     */
    @NotNull List<Simulator> getClients();

    /**
     * Returns the client that has the given client id.
     *
     * @param id the clientId to search for
     * @return the client with the given clientId.
     */
    @NotNull Optional<Simulator> getClient(int id);

    /**
     * Returns all the resources registered to this simulation
     * @return all registered resources
     */
    @NotNull List<Resource> getResources();

    /**
     * Loads the resource at the given path into the list of known resources.
     * This requires a 'fxmanifest.lua' file in the given folder.
     *
     * @throws IllegalArgumentException if the given path doesn't lead to a valid resource.
     *
     * @param path the path to load as resource.
     */
    void load(Path path);

    /**
     * Starts the resource with the given name.
     * @param name the name of the resource to start
     *
     * @throws IllegalArgumentException if the resource with the given name doesn't exist
     */
    void start(String name);

    /**
     * Returns the context of this simulation.
     * @return the context of this simulation
     */
    SimulationContext getContext();
}
