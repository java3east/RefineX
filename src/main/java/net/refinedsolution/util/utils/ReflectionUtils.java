package net.refinedsolution.util.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ReflectionUtils {
    /**
     * Returns the empty constructor of a class.
     * @param clazz the class to get the empty constructor of
     * @return the empty constructor of the class
     *
     * @throws RuntimeException if the class does not have an empty constructor
     */
    public static Constructor<?> findEmptyConstructor(Class<?> clazz) {
        try {
            return clazz.getConstructor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new instance of a class using the empty constructor.
     * @param clazz the class to create an instance of
     * @return the new instance of the class
     *
     * @throws RuntimeException if the class does not have an empty constructor, or
     *          the object could not be created
     */
    public static Object createInstance(Class<?> clazz) {
        Constructor<?> constructor = findEmptyConstructor(clazz);
        try {
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the value of a given field in the given object
     * @param obj the object to set the field in
     * @param fieldName the name of the field to set
     * @param value the value to set
     */
    public static void set(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the type the given field in the given class accepts
     * @param clazz the class to get the field from
     * @param fieldName the name of the field to get
     * @return the type of the field
     */
    public static Class<?> getFieldType(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field.getType();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
