package net.refinedsolution.context.helix;

import com.moandjiezana.toml.Toml;
import net.refinedsolution.context.helix.pkg.PackageToml;
import net.refinedsolution.resource.Manifest;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class HelixManifest implements Manifest {
    private final HashMap<String, List<String>> meta = new HashMap<>();

    @Override
    public void add(@NotNull String key, @NotNull String value) {
        List<String> values = meta.getOrDefault(key, List.of());
        values.add(value);
        meta.put(key, values);
    }

    @Override
    public void addAll(@NotNull String key, @NotNull List<String> values) {
        List<String> existingValues = meta.getOrDefault(key, List.of());
        existingValues.addAll(values);
        meta.put(key, existingValues);
    }

    @Override
    public @NotNull String[] getMeta(@NotNull String key) {
        return meta.getOrDefault(key, List.of()).toArray(new String[0]);
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
        System.out.println(packageToml);
    }

    @Override
    public boolean verify() {
        return true;
    }
}
