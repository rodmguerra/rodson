package io.github.rodmguerra.rodson.readers.util;

import io.github.rodmguerra.rodson.commons.Type;
import io.github.rodmguerra.rodson.json.Json;
import io.github.rodmguerra.rodson.json.JsonObject;
import io.github.rodmguerra.rodson.json.JsonString;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapReader implements MapJsonObjectReader {
    @Override
    public <K, V> Map<K, V> readMapOf(Type<K> keyType, Type<V> valueType, RootReader r, JsonObject json) {
        Map<K, V> map = new LinkedHashMap<>();
        for (Map.Entry<String, Json> prop : json) {
            K key;
            try {
                key = r.read(keyType, JsonString.of(prop.getKey()));
            } catch (RuntimeException e) {
                key = r.read(keyType, Json.parse(prop.getKey()));
            }
            V value = r.read(valueType, prop.getValue());
            map.put(key, value);
        }
        return map;
    }
}
