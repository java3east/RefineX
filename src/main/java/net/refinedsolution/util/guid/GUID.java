package net.refinedsolution.util.guid;

import net.refinedsolution.lua.ACastable;
import net.refinedsolution.lua.castable.CCastable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Optional;

/**
 * Global Unique Identifiers (GUID) are used to uniquely identify objects throughout the java - lua bridge, allowing
 * to refer to java objects in lua scripts.
 * @author Java3east
 */
@ACastable(isDirect = true, castType = LuaInteger.class)
public class GUID extends CCastable<Long> {
    private static long nextId = 0;
    private static final HashMap<GUID, GUIDHolder> idMap = new HashMap<>();

    /**
     * Registers a GUID holder and assigns it a new GUID.
     * This will add the GUID holder to the idMap allowing to retrieve it later.
     * @param guidHolder the GUID holder to register
     */
    public static void register(@NotNull GUIDHolder guidHolder) {
        GUID guid = new GUID();
        idMap.put(guid, guidHolder);
        guidHolder.setGUID(guid);
    }

    /**
     * Returns the GUID holder for the given GUID.
     * If no GUID holder is found, an empty GUID holder will be returned.
     * @param guid the GUID to get the holder for
     * @return the GUID holder for the given GUID
     */
    public static @NotNull GUIDHolder get(@NotNull GUID guid) {
        GUIDHolder holder = idMap.get(guid);
        return holder == null ? new EmptyGUIDHolder() : holder;
    }

    /**
     * Returns the GUID holder of the given type.
     * @param guid the guid to search for
     * @param type the type the holder should be
     * @return the GUID holder of the given type
     */
    public static GUIDHolder get(@NotNull GUID guid, @NotNull Class<? extends GUIDHolder> type) {
        GUIDHolder holder = idMap.get(guid);
        if (holder != null) return holder;
        try {
            Method m = type.getDeclaredMethod("empty");
            Object o = m.invoke(null);
            if (o instanceof GUIDHolder) {
                return (GUIDHolder) o;
            }
        } catch (Exception ignored) { }
        return null;
    }

    public GUID(@NotNull LuaValue value) {
        super(value);
    }

    public GUID(@Nullable Long value) {
        super(value);
    }

     GUID() {
        super(nextId++);
     }

    @Override
    protected @NotNull Long from(@NotNull LuaValue val) {
        return val.checklong();
    }

    @Override
    public String toString() {
        return String.format("GUID:%d", super.get());
    }

    @Override
    public LuaValue lua() {
        return LuaValue.valueOf(super.get());
    }
}
