package io.github.rodmguerra.rodson.readers.util;

import io.github.rodmguerra.rodson.json.JsonArray;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.util.List;

public interface TypedListJsonArrayReader extends TypedIterableJsonArrayReader {
    @Override
    public <T> List<T> readIterableOf(Class<? extends T> elementType, RootReader r, JsonArray jsonArray);
}

