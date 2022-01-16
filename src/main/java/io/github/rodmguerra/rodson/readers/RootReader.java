package io.github.rodmguerra.rodson.readers;

import io.github.rodmguerra.rodson.commons.Type;
import io.github.rodmguerra.rodson.json.Json;
import io.github.rodmguerra.rodson.json.JsonArray;
import io.github.rodmguerra.rodson.json.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RootReader {
    <T> T read(Type<T> type, Json json);
    <T> T read(Class<T> clazz, Json json);
    <K,V> Map<K,V> readMapOf(Class<K> keyType,  Class<V> valueType, JsonObject json);
    <K,V> Map<K,V> readMapOf(Type<K> keyType,  Type<V> valueType, JsonObject json);
    <E> List<E> readListOf(Class<E> elementClass, JsonArray json);
    <E> Set<E> readSetOf(Class<E> clazz, JsonArray json);
    <E> E[] readArrayOf(Class<E> clazz, JsonArray json);
}
