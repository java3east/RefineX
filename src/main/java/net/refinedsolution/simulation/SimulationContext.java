package net.refinedsolution.simulation;

import net.refinedsolution.context.fivem.FivemResourceLoader;
import net.refinedsolution.context.helix.HelixResourceLoader;
import net.refinedsolution.resource.ResourceLoader;

public enum SimulationContext {
    FIVEM(new String[] {

    }, new String[] {

    }, new FivemResourceLoader()),

    HELIX(new String[] {
            "lib/helix/client/class.lua",
            "lib/helix/client/billboard.lua"
    }, new String[]{
            "lib/helix/server/class.lua"
    }, new HelixResourceLoader())
;

    private final String[] clLibraries;
    private final String[] svLibraries;
    private final ResourceLoader loader;

    SimulationContext(String[] clLibraries, String[] svLibraries, ResourceLoader loader) {
        this.clLibraries = clLibraries;
        this.svLibraries = svLibraries;
        this.loader = loader;
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
}
