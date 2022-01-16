package io.github.rodmguerra.rodson.readers.lang;

import io.github.rodmguerra.rodson.json.JsonArray;
import io.github.rodmguerra.rodson.readers.RootReader;

public interface TypedJsonArrayReader {
    <T> T[] readArrayOf(Class<T> elementType, RootReader r, JsonArray jsonArray);
}
