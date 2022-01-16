package io.github.rodmguerra.rodson.writers;

import io.github.rodmguerra.rodson.json.Json;

public interface RootWriter {
    Json write(Object object);
    default String writeToString(Object object) {
        return write(object).toString();
    }



}
