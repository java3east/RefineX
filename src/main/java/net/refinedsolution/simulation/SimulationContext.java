package net.refinedsolution.simulation;

public enum SimulationContext {
    FIVEM(new String[] {

    }),
    HELIX(new String[] {
            "lib/helix/class.lua",
            "lib/helix/billboard.lua"
    })
    ;

    private final String[] libraries;

    SimulationContext(String[] libraries) {
        this.libraries = libraries;
    }

    String[] getLibraries() {
        return libraries;
    }
}
