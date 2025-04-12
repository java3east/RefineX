package net.refinedsolution.resource;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A default Resource implementation
 * @author Java3east
 */
public class ResourceImpl implements Resource {
    private final Path path;

    /**
     * Creates a new resource from its path
     * @param path the path to the resource
     */
    public ResourceImpl(String path) {
        this.path = Paths.get(path);
    }

    @Override
    public @NotNull File getLocation() {
        return this.path.toFile();
    }

    @Override
    public @NotNull String getName() {
        return "";
    }
}
