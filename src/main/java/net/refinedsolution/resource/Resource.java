package net.refinedsolution.resource;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * A resource.
 * @author Java3east
 */
public interface Resource {
    /**
     * Returns the folder in which the resource is located
     * @return the folder of the resource
     */
    @NotNull File getLocation();

    /**
     * Returns the name of this resource
     * @return the name of this resource
     */
    @NotNull String getName();
}
