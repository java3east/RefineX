package net.refinedsolution.resource;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * A default Resource implementation
 * @author Java3east
 */
public class ResourceImpl implements Resource {
    private final Path path;
    private final Manifest manifest;
    /**
     * Creates a new resource from its path
     * @param path the path to the resource
     */
    public ResourceImpl(@NotNull String path, @NotNull Manifest manifest) {
        this.path = Paths.get(path);
        this.manifest = manifest;
    }

    @Override
    public @NotNull File getLocation() {
        return this.path.toFile();
    }

    @Override
    public @NotNull String getName() {
        Optional<String> name = this.manifest.getName();
        return name.orElseGet(() -> this.path.getFileName().toString());
    }

    @Override
    public @NotNull Manifest getManifest() {
        return manifest;
    }
}
