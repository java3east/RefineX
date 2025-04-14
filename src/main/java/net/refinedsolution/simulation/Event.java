package net.refinedsolution.simulation;

import net.refinedsolution.lua.ACastable;
import net.refinedsolution.lua.castable.*;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Events can be dispatched on simulators.
 * @author Java3east
 */
@ACastable
public interface Event {
    /**
     * Sets the name of this event
     * @param name the name of this event
     */
    @ACastable.Field(name = "name")
    void setName(@NotNull CString name);

    /**
     * Marks / unmarks this event as a network event
     * @param networked true if the event should be a network event
     */
    @ACastable.Field(name = "isNet")
    void setNetworked(@NotNull CBool networked);

    /**
     * The source of this network event (-1 for server)
     * @param source the source of this event
     */
    @ACastable.Field(name = "source")
    void setSource(@NotNull CInt source);

    /**
     * Sets the destination of this event.
     * This value will be ignored if the source is something else than -1 (not the server)
     * @param destination the id of the client (-1 for all clients)
     */
    @ACastable.Field(name = "destination")
    void setDestination(@NotNull CInt destination);

    /**
     * Sets a data value of this event
     * @param key the key to set the value for
     * @param value the value to set
     */
    void set(@NotNull CCastable<?> key, @NotNull CCastable<?> value);

    /**
     * Returns the value for the given key
     * @param key the key to get the value for
     * @return the value
     */
    @NotNull Optional<CCastable<?>> get(@NotNull CCastable<?> key);

    /**
     * Returns the value for the given key, if it doesn't exist the default value will be returned
     * @param key the key to get the value for
     * @param value the fallback value
     * @return the value or the fallback value
     */
    @NotNull CCastable<?> get(@NotNull CCastable<?> key, @NotNull CCastable<?> value);

    /**
     * Returns the set name of this event
     * @return the name of the event
     *
     * @throws RuntimeException if the event name has not yet been set
     */
    @ACastable.PackField(name = "name")
    @NotNull CString getName();

    /**
     * Returns whether this event is a network event
     * @return a true CBool if this is a network event
     */
    @ACastable.PackField(name = "isNet")
    @NotNull CBool isNetworked();

    /**
     * Returns the source this event is coming from
     * @return the source of this event
     */
    @ACastable.PackField(name = "source")
    @NotNull CInt getSource();

    /**
     * Returns the destination of this event
     * @return the destination
     */
    @ACastable.PackField(name = "destination")
    @NotNull CInt getDestination();
}
