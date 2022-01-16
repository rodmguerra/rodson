package io.github.rodmguerra.rodson.readers.lang;

import io.github.rodmguerra.rodson.commons.Type;
import io.github.rodmguerra.rodson.json.Json;
import io.github.rodmguerra.rodson.json.JsonObject;
import io.github.rodmguerra.rodson.readers.JsonObjectReader;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ObjectSetterReader implements JsonObjectReader<Object> {

    private final boolean ignoreUnmappedProperties;

    private static final String SET = "set";

    public ObjectSetterReader(boolean ignoreUnmappedProperties) {
        this.ignoreUnmappedProperties = ignoreUnmappedProperties;
    }

    @Override
    public Object read(RootReader r, Class<Object> clazz, JsonObject json) {
        Object object = newInstance(clazz);

        for (Map.Entry<String, Json> prop : json) {
            Method setter = setterFor(prop.getKey(), clazz.getMethods());
            if (setter == null) {
                if (!ignoreUnmappedProperties) throw new UnsupportedOperationException(
                        String.format("Property '%s' is not available in %s, json = %s", prop.getKey(), clazz.getName(), json));
            } else {
                Type propType = Type.of(setter.getGenericParameterTypes()[0]);
                invokeSetter(object, setter, r.read(propType, prop.getValue()));
            }
        }
        return object;
    }

    private Method setterFor(String key, Method[] methods) {
        for (Method method : methods) {
            String methodName = method.getName();
            if (method.getParameterCount() == 1 && methodName.startsWith(SET)) {
                String propKey = methodName.substring(SET.length());
                propKey = propKey.substring(0, 1).toLowerCase() + propKey.substring(1);
                if (key.equals(propKey)) return method;
            }
        }
        return null;
    }

    private Object newInstance(Class<?> clazz) {
        Object object;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
        return object;
    }

    private void invokeSetter(Object object, Method method, Object value) {
        try {
            method.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
