package io.github.rodmguerra.rodson.commons;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReflectionUtils {

    public static <T> Class<T> wrap(Class<T> c) {
        return c.isPrimitive() ? (Class<T>) PRIMITIVES_TO_WRAPPERS.get(c) : c;
    }

    public static <T> Class<T> rawClass(java.lang.reflect.Type type) {
        if(type instanceof Class) return (Class) type;
        else return rawClass(((ParameterizedType) type).getRawType());
    }

    public static <E extends Enum<E>> E enumConstant(Class<? extends E> clazz, int ordinal) {
        try {
        return clazz.getEnumConstants()[ordinal];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("Ordinal '%d' is not valid for enum %s", ordinal, clazz), e);
        }
    }

    public static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS
            = new LinkedHashMap<>();

    static {
        PRIMITIVES_TO_WRAPPERS.put(boolean.class, Boolean.class);
        PRIMITIVES_TO_WRAPPERS.put(byte.class, Byte.class);
        PRIMITIVES_TO_WRAPPERS.put(char.class, Character.class);
        PRIMITIVES_TO_WRAPPERS.put(double.class, Double.class);
        PRIMITIVES_TO_WRAPPERS.put(float.class, Float.class);
        PRIMITIVES_TO_WRAPPERS.put(int.class, Integer.class);
        PRIMITIVES_TO_WRAPPERS.put(long.class, Long.class);
        PRIMITIVES_TO_WRAPPERS.put(short.class, Short.class);
        PRIMITIVES_TO_WRAPPERS.put(void.class, Void.class);
    }
}
