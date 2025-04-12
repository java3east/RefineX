package net.refinedsolution.context.helix.pkg;

import java.util.Arrays;
import java.util.Objects;

public class Script {
    public boolean force_no_map_package;
    public boolean auto_cleanup;
    public boolean load_level_entities;
    public String[] packages_requirements;
    public String[] assets_requirements;
    public String[] compatible_game_modes;

    public String[] get_packages_requirements() {
        return Objects.requireNonNullElseGet(packages_requirements, () -> new String[0]);
    }

    public String[] get_assets_requirements() {
        return Objects.requireNonNullElseGet(assets_requirements, () -> new String[0]);
    }

    public String[] get_compatible_game_modes() {
        return Objects.requireNonNullElseGet(compatible_game_modes, () -> new String[0]);
    }

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
