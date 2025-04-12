package net.refinedsolution.simulation.world;

import net.refinedsolution.lua.castable.CFunction;
import net.refinedsolution.simulation.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Represents a base entity which might be shared over the network.
 * @author Java3east
 */
public interface Entity {
    /**
     * Returns the potential network id of this entity.
     * @return the network id of this entity, if it has one
     */
    Optional<Integer> getNetworkId();

    /**
     * Returns all the entities linked to this entity.
     * (The same entity on the server / different client)
     * @return a list of linked entities
     */
    List<Entity> linked();

    /**
     * Returns the world this entity is in.
     * @return the world this entity is in
     */
    World getWorld();

    /**
     * Sets a value for a given key on this entity.
     * @param key the key to set
     * @param value the value to set
     * @param synchronize whether to synchronize this value over the network
     */
    void setValue(@NotNull String key, @Nullable Object value, boolean synchronize);

    /**
     * Returns the value for the given key on this entity.
     * @param key the key to get the value for
     * @param fallback a fallback value
     * @return the value for the given key, or the fallback value if not found
     */
    Optional<Object> getValue(@NotNull String key, @Nullable Object fallback);

    /**
     * Adds an event listener for the given key on this entity.
     * @param key the key to register the event for
     * @param function the function to call when the event is triggered
     * @return the function that was registered
     */
    @NotNull CFunction subscribe(@NotNull String key, @NotNull CFunction function);

    /**
     * Adds a network event listener for the given key on this entity.
     * @param key the key to register the event for
     * @param function the function to call when the event is triggered
     * @return the function that was registered
     */
    @NotNull CFunction subscribeRemote(@NotNull String key, @NotNull CFunction function);

    /**
     * Unsubscribes the given function from the given key on this entity.
     * @param key the key to unsubscribe from
     * @param function the function to unsubscribe (optional)
     */
    void unsubscribe(@NotNull String key, Optional<CFunction> function);

    /**
     * Calls the given event on this entity for the given player.
     * @param key the key to call the event for
     * @param player the player to call the event for
     * @param args the arguments to pass to the event
     *
     * @throws RuntimeException if this function is called on a client
     */
    void callRemote(@NotNull String key, @NotNull Player player, Object... args);

    /**
     * Calls the given event on this entity for the server
     * @param key the key to call the event for
     * @param args the arguments to pass to the event
     *
     * @throws RuntimeException if this function is called on the server
     */
    void callRemote(@NotNull String key, Object... args);

    /**
     * Calls the given event on this entity for all players.
     * @param key the key to call the event for
     * @param args the arguments to pass to the event
     *
     * @throws RuntimeException if this function is called on a client
     */
    void broadcast(@NotNull String key, Object... args);

    /**
     * Removes this entity from the world.
     */
    void destroy();

    /**
     * Returns the value for the given key on this entity.
     * @param key the key to get the value for
     * @return the value for the given key, or an empty optional if not found
     */
    default Optional<Object> getValue(@NotNull String key) {
        return getValue(key, null);
    }
}
