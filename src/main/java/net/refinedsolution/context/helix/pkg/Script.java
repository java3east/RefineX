package net.refinedsolution.context.helix.pkg;

import java.util.Arrays;

public class Script {
    public boolean force_no_map_package;
    public boolean auto_cleanup;
    public boolean load_level_entities;
    public String[] packages_requirements;
    public String[] assets_requirements;
    public String[] compatible_game_modes;

    @Override
    public String toString() {
        return "Script{" +
                "force_no_map_package=" + force_no_map_package +
                ", auto_cleanup=" + auto_cleanup +
                ", load_level_entities=" + load_level_entities +
                ", packages_requirements=" + Arrays.toString(packages_requirements) +
                ", assets_requirements=" + Arrays.toString(assets_requirements) +
                ", compatible_game_modes=" + Arrays.toString(compatible_game_modes) +
                '}';
    }
}
