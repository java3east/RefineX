package net.refinedsolution.context.helix;

import com.moandjiezana.toml.Toml;
import net.refinedsolution.context.helix.pkg.PackageToml;
import net.refinedsolution.resource.Manifest;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;
import java.util.*;

public class HelixManifest implements Manifest {
    private final HashMap<String, List<String>> meta = new HashMap<>();

    @Override
    public void add(@NotNull String key, @NotNull String value) {
        List<String> values = meta.getOrDefault(key, new ArrayList<>());
        values.add(value);
        meta.put(key, values);
    }

    @Override
    public void addAll(@NotNull String key, @NotNull List<String> values) {
        List<String> existingValues = meta.getOrDefault(key, new ArrayList<>());
        existingValues.addAll(values);
        meta.put(key, existingValues);
    }

    @Override
    public @NotNull String[] getMeta(@NotNull String key) {
        return meta.getOrDefault(key, new ArrayList<>()).toArray(new String[0]);
    }

    @Override
    public @NotNull Optional<String> getMeta(@NotNull String key, int index) {
        String[] values = getMeta(key);
        if (index < 0 || index >= values.length) {
            return Optional.empty();
        }
        return Optional.of(values[index]);
    }

    @Override
    public boolean isSet(String key) {
        return meta.containsKey(key);
    }

    @Override
    public boolean isSet(String key, int index) {
        return isSet(key) && index >= 0 && index < meta.get(key).size();
    }

    @Override
    public void load(@NotNull String path) {
        PackageToml packageToml = new Toml().read(Paths.get(path + "/Package.toml").toFile()).to(PackageToml.class);
        add("title", packageToml.meta.title);
        add("author", packageToml.meta.author);
        add("version", packageToml.meta.version);
        add("force_no_map_package", packageToml.script.force_no_map_package ? "yes" : "no");
        add("auto_cleanup", packageToml.script.auto_cleanup ? "yes" : "no");
        add("load_level_entities", packageToml.script.load_level_entities ? "yes" : "no");
        addAll("packages_requirements", Arrays.stream(packageToml.script.get_packages_requirements()).toList());
        addAll("assets_requirements", Arrays.stream(packageToml.script.get_assets_requirements()).toList());
        addAll("compatible_game_modes", Arrays.stream(packageToml.script.get_compatible_game_modes()).toList());
    }

    @Override
    public boolean verify() {
        boolean ok = true;

        if (!isSet("title", 0)) ok = false;
        if (!isSet("author", 0)) ok = false;
        if (!isSet("version", 0)) ok = false;
        if (!isSet("force_no_map_package", 0)) ok = false;
        if (!isSet("auto_cleanup", 0)) ok = false;
        if (!isSet("load_level_entities", 0)) ok = false;
        if (!isSet("packages_requirements")) ok = false;
        if (!isSet("assets_requirements")) ok = false;
        if (!isSet("compatible_game_modes")) ok = false;

        return ok;
    }

    @Override
    public @NotNull Optional<String> getName() {
        return getMeta("title", 0);
    }
}
