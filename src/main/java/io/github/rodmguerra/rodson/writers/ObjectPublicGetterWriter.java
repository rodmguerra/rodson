package io.github.rodmguerra.rodson.writers;


import io.github.rodmguerra.rodson.json.JsonObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectPublicGetterWriter implements JsonWriter<Object, JsonObject> {

    public static final String GET = "get";

    @Override
    public JsonObject write(RootWriter w, Object object) {
        Method[] methods = object.getClass().getMethods();
        JsonObject.Builder builder = JsonObject.builder();
        for (Method method : methods) {
            String methodName = method.getName();
            if (method.getParameterCount() == 0 && methodName.startsWith(GET)) {
                String propKey = methodName.substring(GET.length());
                propKey = propKey.substring(0, 1).toLowerCase() + propKey.substring(1);
                Object propValue = invokeGetter(object, method);
                builder.add(propKey, w.write(propValue));
            }
        }
        return builder.build();
    }

    private Object invokeGetter(Object object, Method method) {
        try {
            return method.invoke(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
