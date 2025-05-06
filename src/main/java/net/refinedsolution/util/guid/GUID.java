package net.refinedsolution.util.guid;

import net.refinedsolution.lua.castable.ICastable;
import net.refinedsolution.util.utils.ReflectionUtils;
import org.jetbrains.annotations.NotNull;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.util.HashMap;
import java.util.Objects;

/**
 * Global Unique Identifiers (GUID) are used to uniquely identify objects throughout the java - lua bridge, allowing
 * to refer to java objects in lua scripts.
 * @author Java3east
 */
public class GUID  implements ICastable {
    private static long nextId = 0;
    private static final HashMap<GUID, GUIDHolder> idMap = new HashMap<>();

    /**
     * Registers a GUID holder and assigns it a new GUID.
     * This will add the GUID holder to the idMap allowing to retrieve it later.
     * @param guidHolder the GUID holder to register
     */
    public static void register(@NotNull GUIDHolder guidHolder) {
        GUID guid = new GUID(true);
        idMap.put(guid, guidHolder);
        ReflectionUtils.set(guidHolder, "guid", guid);
    }

    /**
     * Returns the GUID holder for the given GUID.
     * If no GUID holder is found, an empty GUID holder will be returned.
     * @param guid the GUID to get the holder for
     * @return the GUID holder for the given GUID
     */
    public static @NotNull GUIDHolder get(@NotNull GUID guid) {
        GUIDHolder holder = idMap.get(guid);
        if (holder == null)
            throw new NullPointerException("No such guid: " + guid);
        return holder;
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
        throw new IllegalArgumentException("No such guid: " + guid);
    }

    private long id;

    public GUID(boolean _new) {
        this.id = ++nextId;
    }

    public GUID() {}

    public long getId() {
        return id;
    }

    @Override
    public LuaValue lua() {
        LuaTable tbl = new LuaTable();
        tbl.set("id", id);
        return tbl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GUID guid = (GUID) o;
        return id == guid.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GUID{" +
                "id=" + id +
                '}';
    }
}
