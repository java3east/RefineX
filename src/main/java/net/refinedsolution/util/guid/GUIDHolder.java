package net.refinedsolution.util.guid;

import org.jetbrains.annotations.NotNull;

/**
 * GUID holders are objects that can hold a GUID.
 * This interface is used to set the GUID of an object.
 * @author Java3east
 */
public interface GUIDHolder {
    /**
     * Returns the global object id of this object.
     * @return the global object id
     */
    @NotNull GUID getGUID();
}
