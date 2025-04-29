package net.refinedsolution.util.guid;

import org.jetbrains.annotations.NotNull;

/**
 * An empty GUID holder that does not hold any GUID.
 * This is used to represent an empty GUID holder.
 * @author Java3east
 */
public class EmptyGUIDHolder implements GUIDHolder {
    private GUID guid;

    @Override
    public void setGUID(@NotNull GUID guid) {
        this.guid = guid;
    }

    @Override
    public @NotNull GUID getGUID() {
        return guid;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
