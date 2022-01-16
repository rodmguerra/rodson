package io.github.rodmguerra.rodson.readers.util;

import io.github.rodmguerra.rodson.json.JsonArray;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.util.Set;

public interface TypedSetJsonArrayReader extends TypedIterableJsonArrayReader {
    @Override
    public <T> Set<T> readIterableOf(Class<? extends T> elementType, RootReader r, JsonArray jsonArray);
}

