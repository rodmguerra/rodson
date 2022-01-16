package io.github.rodmguerra.rodson.readers.lang;

import io.github.rodmguerra.rodson.json.Json;
import io.github.rodmguerra.rodson.json.JsonArray;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.lang.reflect.Array;

public class JavaArrayReader implements TypedJsonArrayReader {

    @Override
    public <T> T[] readArrayOf(Class<T> elementType, RootReader r, JsonArray jsonArray) {
        T[] array = (T[]) Array.newInstance(elementType, jsonArray.size());
        int i = 0;
        for (Json json : jsonArray) {
            array[i++] = r.read(elementType, json);
        }
        return array;
    }
}
