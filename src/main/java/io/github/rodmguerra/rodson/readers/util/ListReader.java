package io.github.rodmguerra.rodson.readers.util;

import io.github.rodmguerra.rodson.json.Json;
import io.github.rodmguerra.rodson.json.JsonArray;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.util.ArrayList;
import java.util.List;

public class ListReader implements TypedListJsonArrayReader {
    @Override
    public <T> List<T> readIterableOf(Class<? extends T> elementType, RootReader r, JsonArray jsonArray) {
        List<T> list = new ArrayList<>(jsonArray.size());
        for (Json json : jsonArray) {
            list.add(r.read(elementType, json));
        }
        return list;
    }
}
