package net.refinedsolution.simulation;

import net.refinedsolution.context.fivem.FivemResourceLoader;
import net.refinedsolution.context.fivem.FivemWorld;
import net.refinedsolution.context.helix.HelixResourceLoader;
import net.refinedsolution.context.helix.HelixWorld;
import net.refinedsolution.resource.ResourceLoader;

public enum SimulationContext {
    FIVEM(new String[] {

    }, new String[] {

    }, new FivemResourceLoader(), new FivemWorld(null)),

    HELIX(new String[] {
            "lib/helix/client/event.lua",
            "lib/helix/eventmanager.lua",
            "lib/helix/client/class.lua",
            "lib/helix/client/entity.lua",
            "lib/helix/client/billboard.lua",
    }, new String[] {
            "lib/helix/server/event.lua",
            "lib/helix/eventmanager.lua",
            "lib/helix/server/class.lua"
    }, new HelixResourceLoader(), new HelixWorld(null))
;

    private final String[] clLibraries;
    private final String[] svLibraries;
    private final ResourceLoader loader;
    private final World world;

    SimulationContext(String[] clLibraries, String[] svLibraries, ResourceLoader loader, World world) {
        this.clLibraries = clLibraries;
        this.svLibraries = svLibraries;
        this.loader = loader;
        this.world = world;
    }

    /**
     * Returns the client lua libraries to load
     * @return the libraries that should be loaded on the client
     */
    public String[] getClientLibraries() {
        return clLibraries;
    }

    /**
     * Returns the server lua libraries to load
     * @return the libraries that should be loaded on the server
     */
    public String[] getServerLibraries() {
        return svLibraries;
    }

    /**
     * Returns the resource loader for this simulation context
     * @return the resource loader
     */
    public ResourceLoader getResourceLoader() {
        return this.loader;
    }

    /**
     * Returns the world template of this simulation context
     * @return the world
     */
    public World getWorld() {
        return this.world.copy();
    }
}
