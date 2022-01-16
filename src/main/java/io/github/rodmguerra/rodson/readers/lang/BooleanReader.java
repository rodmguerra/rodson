package io.github.rodmguerra.rodson.readers.lang;

import io.github.rodmguerra.rodson.json.JsonBoolean;
import io.github.rodmguerra.rodson.readers.JsonBooleanReader;
import io.github.rodmguerra.rodson.readers.RootReader;

public class BooleanReader implements JsonBooleanReader<Boolean> {
    @Override
    public Boolean read(RootReader r, Class<Boolean> clazz, JsonBoolean json) {
        return json.booleanValue();
    }
}
