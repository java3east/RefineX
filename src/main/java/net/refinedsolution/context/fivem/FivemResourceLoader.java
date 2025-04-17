package net.refinedsolution.context.fivem;

import net.refinedsolution.resource.Resource;
import net.refinedsolution.resource.ResourceImpl;
import net.refinedsolution.resource.ResourceLoader;
import net.refinedsolution.simulation.Simulator;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to load FiveM resources.
 * @author Java3east
 */
public class FivemResourceLoader implements ResourceLoader {
    @Override
    public @NotNull Resource load(@NotNull String path) {
        FivemManifest manifest = new FivemManifest();
        manifest.load(path);
        if (!manifest.verify())
            throw new IllegalStateException("Manifest is not valid");
        return new ResourceImpl(path, new FivemManifest());
    }

    @Override
    public void start(@NotNull Simulator simulator, @NotNull Resource resource, int mutation) {
    }
}
