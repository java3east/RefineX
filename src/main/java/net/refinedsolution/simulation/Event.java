package net.refinedsolution.simulation;

import net.refinedsolution.lua.ACastable;
import net.refinedsolution.lua.castable.*;
import org.jetbrains.annotations.NotNull;

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
    Event setName(@NotNull CString name);
    Event setName(@NotNull String name);

    /**
     * Marks / unmarks this event as a network event
     * @param networked true if the event should be a network event
     */
    @ACastable.Field(name = "isNet")
    Event setNetworked(@NotNull CBool networked);
    Event setNetworked(boolean networked);

    /**
     * The source of this network event (-1 for server)
     * @param source the source of this event
     */
    @ACastable.Field(name = "source")
    Event setSource(@NotNull CInt source);
    Event setSource(int source);

    /**
     * Sets the destination of this event.
     * This value will be ignored if the source is something else than -1 (not the server)
     * @param destination the id of the client (-1 for all clients)
     */
    @ACastable.Field(name = "destination")
    Event setDestination(@NotNull CInt destination);
    Event setDestination(int destination);

    /**
     * Sets the event call parameters
     * @param data the parameters of this event
     */
    @ACastable.Field(name = "data")
    Event setData(@NotNull CCastable<?>[] data);

    /**
     * Returns the event call parameters
     * @return the parameters of this event
     */
    @ACastable.PackField(name = "data")
    CCastable<?>[] getData();

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
