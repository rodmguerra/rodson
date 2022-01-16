package io.github.rodmguerra.rodson.writers;


import io.github.rodmguerra.rodson.json.JsonArray;

public class ArrayWriter implements JsonWriter<Object[], JsonArray> {
    @Override
    public JsonArray write(RootWriter w, Object[] array) {
        JsonArray.Builder json = JsonArray.builder();
        for (Object element : array) {
            json.add(w.write(element));
        }
        return json.build();
    }
}
