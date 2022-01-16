package io.github.rodmguerra.rodson.readers.util;

import io.github.rodmguerra.rodson.json.Json;
import io.github.rodmguerra.rodson.json.JsonArray;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.util.LinkedHashSet;
import java.util.Set;

public class SetReader implements TypedSetJsonArrayReader {
    @Override
    public <T> Set<T> readIterableOf(Class<? extends T> elementType, RootReader r, JsonArray jsonArray) {
        Set<T> set = new LinkedHashSet<>(jsonArray.size());
        for (Json json : jsonArray) {
            set.add((T) r.read(elementType, json));
        }
        return set;
    }
}
