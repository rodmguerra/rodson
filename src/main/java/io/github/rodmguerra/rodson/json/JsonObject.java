package io.github.rodmguerra.rodson.json;

import java.util.*;
import java.util.function.Consumer;

public class JsonObject implements Json, Iterable<Map.Entry<String,Json>>{
    private final Map<String, Json> properties;

    public JsonObject(Map<String, Json> properties) {
        this.properties = new LinkedHashMap<>(properties);
    }

    public static JsonObject of(Map<String, Json> properties) {
        return new JsonObject(properties);
    }

    @Override
    public String toString() {
        List<String> props = new ArrayList<>(properties.size());
        for (String property : properties.keySet()) {
            props.add(JsonString.of(property) + ":" + properties.get(property));
        }
        return "{" + String.join(",", props.toArray(new String[0])) + "}";

    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Iterator<Map.Entry<String, Json>> iterator() {
        return new ArrayList<>(properties.entrySet()).iterator();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<String, Json>> action) {
        new ArrayList<>(properties.entrySet()).forEach(action);
    }

    @Override
    public Spliterator<Map.Entry<String, Json>> spliterator() {
        return new ArrayList<>(properties.entrySet()).spliterator();
    }

    public Json get(String key) {
        return properties.get(key);
    }

    public static class Builder {
        private Map<String,Json> properties = new LinkedHashMap<>();

        public Builder add(String key, Json value) {
            properties.put(key, value);
            return this;
        }

        public JsonObject build() {
            return JsonObject.of(properties);
        }
    }
}
