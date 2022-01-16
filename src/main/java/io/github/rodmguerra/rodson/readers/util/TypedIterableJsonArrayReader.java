package io.github.rodmguerra.rodson.readers.util;

import io.github.rodmguerra.rodson.json.JsonArray;
import io.github.rodmguerra.rodson.readers.RootReader;

public interface TypedIterableJsonArrayReader  {
    <T> Iterable<T> readIterableOf(Class<? extends T> elementType, RootReader r, JsonArray jsonArray);
}

