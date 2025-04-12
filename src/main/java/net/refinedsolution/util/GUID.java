package net.refinedsolution.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Global Unique Identifier
 * @author Java3east
 */
public class GUID {
    private static long nextId = 0;
    private static final HashMap<Long, Object> idMap = new HashMap<>();

    /**
     * Creates a new unique id for the given object.
     * This will save it in the map of identifiable objects.
     * @param object the object to identify
     * @return the unique identifier
     */
    public static synchronized long identify(@NotNull Object object) {
        for (Long key : idMap.keySet())
            if (idMap.get(key) == object) return key;
        long id = nextId++;
        idMap.put(id, object);
        return id;
    }

    /**
     * Returns the Object associated with the given id.
     * @param id the id to get the object for
     * @return the object identified by the given id or null if it doesn't exist
     */
    public static synchronized @Nullable Object identify(long id) {
        return idMap.get(id);
    }

    /**
     * Returns the Object associated with the given id. This will also check if the class matches the given type.
     * @param id the id to get the object for
     * @param clazz the expected class the object should have
     * @return the object identified by the given id
     */
    public static synchronized @NotNull Object identify(long id, @NotNull Class<?> clazz) {
        Object o = idMap.get(id);
        if (!clazz.isInstance(o)) throw new ClassCastException("Class " + o.getClass().getName() + " is not an instance of " + clazz.getName());
        return o;
    }

    /**
     * Returns weather or not the given id exists in the dictionary
     * @param id the id to check
     * @return true if it exists
     */
    public static synchronized boolean has(long id) {
        return idMap.containsKey(id);
    }

    /**
     * Returns weather or not an object with the given id and class is registered
     * @param id the id to search for
     * @param clazz the expected class type
     * @return true if an object with the given id and type exists
     */
    public static synchronized boolean has(long id, Class<?> clazz) {
        Object o = idMap.get(id);
        return clazz.isInstance(o);
    }
}
