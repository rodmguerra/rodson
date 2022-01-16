package io.github.rodmguerra.rodson.writers;

import io.github.rodmguerra.rodson.json.Json;

public interface JsonWriter<T, J extends Json> {
    J write(RootWriter writer, T object);
}
