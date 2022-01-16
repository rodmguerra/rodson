package io.github.rodmguerra.rodson.readers;
import io.github.rodmguerra.rodson.json.Json;

public interface JsonReader<T, J extends Json> {
    public T read(RootReader r, Class<T> clazz, J json);
}
