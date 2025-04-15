package net.refinedsolution.simulation;

import net.refinedsolution.lua.ACastable;
import net.refinedsolution.lua.castable.CBool;
import net.refinedsolution.lua.castable.CCastable;
import net.refinedsolution.lua.castable.CInt;
import net.refinedsolution.lua.castable.CString;
import org.jetbrains.annotations.NotNull;

@ACastable
public class EventImpl  implements Event {
    private String name;
    private boolean isNetworked = false;
    private int source = -1;
    private int destination = -1;
    private CCastable<?>[] data = new CCastable<?>[0];

    @Override
    @ACastable.Field(name = "name")
    public EventImpl setName(@NotNull CString name) {
        this.name = name.get();
        return this;
    }

    public EventImpl setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    @ACastable.Field(name = "isNet")
    public EventImpl setNetworked(@NotNull CBool networked) {
        this.isNetworked = networked.get();
        return this;
    }

    public EventImpl setNetworked(boolean networked) {
        this.isNetworked = networked;
        return this;
    }

    @Override
    @ACastable.Field(name = "source")
    public EventImpl setSource(@NotNull CInt source) {
        this.source = source.get();
        return this;
    }

    public EventImpl setSource(int source) {
        this.source = source;
        return this;
    }

    @Override
    @ACastable.Field(name = "destination")
    public EventImpl setDestination(@NotNull CInt destination) {
        this.destination = destination.get();
        return this;
    }

    public EventImpl setDestination(int destination) {
        this.destination = destination;
        return this;
    }

    @Override
    @ACastable.Field(name = "data")
    public EventImpl setData(@NotNull CCastable<?>[] data) {
        this.data = data;
        return this;
    }

    @Override
    @ACastable.PackField(name = "data")
    public CCastable<?>[] getData() {
        return data;
    }

    @Override
    @ACastable.PackField(name = "name")
    public @NotNull CString getName() {
        return new CString(name);
    }

    @Override
    @ACastable.PackField(name = "isNet")
    public @NotNull CBool isNetworked() {
        return new CBool(isNetworked);
    }

    @Override
    @ACastable.PackField(name = "source")
    public @NotNull CInt getSource() {
        return new CInt(source);
    }

    @Override
    @ACastable.PackField(name = "destination")
    public @NotNull CInt getDestination() {
        return new CInt(destination);
    }
}
