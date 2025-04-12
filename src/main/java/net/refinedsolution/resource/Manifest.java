package net.refinedsolution.resource;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * Represents a fxmanifest.lua file
 * @author Java3east
 */
public interface Manifest {
    /**
     * Adds the given value to the given key
     * @param key the key to add the value to
     * @param value the value to add
     */
    void add(@NotNull String key, @NotNull String value);

    /**
     * Adds all the given values for the key
     * @param key the key to add the values for
     * @param values the values to add
     */
    void addAll(@NotNull String key, @NotNull List<String> values);

    /**
     * Returns all the values for the given key. Returns an empty array, if
     * the key has not been set
     * @param key the key to get the values for
     * @return the values saved for the given key
     */
    @NotNull String[] getMeta(@NotNull String key);

    /**
     * Returns the value for the given key at the given index.
     * @param key the key to get the value from
     * @param index the index to get
     * @return the value at the given key and index
     */
    @NotNull Optional<String> getMeta(@NotNull String key, int index);

    /**
     * Checks if the given key has been set
     * @param key the key to check
     * @return true if the key has been set
     */
    boolean isSet(String key);

    /**
     * Checks if the given index is available for the given key.
     * @param key the key to check
     * @param index the index to search for
     * @return true if the given index is present for the given key
     */
    boolean isSet(String key, int index);
}
