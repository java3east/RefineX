package net.refinedsolution.util.guid;

import org.jetbrains.annotations.NotNull;

/**
 * GUID holders are objects that can hold a GUID.
 * This interface is used to set the GUID of an object.
 * @author Java3east
 */
public interface GUIDHolder {
    /**
     * Sets the guid of the object
     * @param guid the guid to set
     */
    void setGUID(@NotNull GUID guid);

    /**
     * Returns the global object id of this object.
     * @return the global object id
     */
    @NotNull GUID getGUID();

    /**
     * Returns true if this object is empty (no object is assigned to this guid)
     * @return true if this object is empty
     */
    default boolean isEmpty() { return false; }
}
