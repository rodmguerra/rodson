package io.github.rodmguerra.rodson.writers;


import io.github.rodmguerra.rodson.json.JsonArray;

public class IterableWriter implements JsonWriter<Iterable<?>, JsonArray> {
    @Override
    public JsonArray write(RootWriter w, Iterable<?> iterable) {
        JsonArray.Builder json = JsonArray.builder();
        for (Object element : iterable) {
            json.add(w.write(element));
        }
        return json.build();
    }
}
