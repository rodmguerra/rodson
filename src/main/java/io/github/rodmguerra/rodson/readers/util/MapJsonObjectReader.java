package io.github.rodmguerra.rodson.readers.util;

import io.github.rodmguerra.rodson.commons.Type;
import io.github.rodmguerra.rodson.json.JsonObject;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.util.Map;

public interface MapJsonObjectReader {
    <K,V> Map<K, V> readMapOf(Type<K> keyType, Type<V> valueType, RootReader r, JsonObject json);
}
