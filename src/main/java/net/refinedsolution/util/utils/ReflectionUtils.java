package net.refinedsolution.util.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ReflectionUtils {
    public static Constructor<?> findEmptyConstructor(Class<?> clazz) {
        try {
            return clazz.getConstructor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object createInstance(Class<?> clazz) {
        Constructor<?> constructor = findEmptyConstructor(clazz);
        try {
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void set(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> getFieldType(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field.getType();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
