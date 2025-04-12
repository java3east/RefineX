package net.refinedsolution.lua.castable;

import net.refinedsolution.lua.ACastable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.luaj.vm2.LuaValue;

import java.util.Objects;

/**
 * This is a template for creating single value types that can be easily converted between java and Lua.
 * Values of this type should be used as constructor parameters for native functions since this allows type safety,
 * and they are easy to use.
 * @param <A> the type of the value to store in this castable
 * @author Java3east
 */
@ACastable()
public abstract class CCastable<A> implements ICastable {
    private final A value;

    /**
     * A constructor to set the value form its lua value
     * this should be marked with @ACastable.Direct
     * @param value the lua value
     */
    @ACastable.Direct
    public CCastable(@NotNull LuaValue value) {
        if (value.isnil()) {
            this.value = null;
            return;
        }
        this.value = from(value);
    }

    /**
     * A constructor that directly sets the value from its java value
     * @param value the value to set
     */
    public CCastable(@Nullable A value) {
        this.value = value;
    }

    /**
     * Creates the type of this Castable A from the given lua value
     * @param val the value to get A from
     * @return the java type A
     */
    protected abstract @NotNull A from(@NotNull LuaValue val);

    /**
     * Returns if this castable dose exist or if a nil value was given
     * @return true if the value exists (!= null)
     */
    public final boolean isUndefined() {
        return this.value == null;
    }

    /**
     * Returns the value of this castable as a Java value
     * @return the java value
     * @throws NullPointerException if the value is null
     */
    public final @NotNull A get() {
        if (this.isUndefined()) throw new NullPointerException("Cannot get nil value from castable, check for null first!");
        return this.value;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CCastable<?> castable = (CCastable<?>) o;
        return Objects.equals(value, castable.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
