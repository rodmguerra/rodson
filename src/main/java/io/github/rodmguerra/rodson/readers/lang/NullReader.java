package io.github.rodmguerra.rodson.readers.lang;

import io.github.rodmguerra.rodson.json.JsonNull;
import io.github.rodmguerra.rodson.readers.JsonNullReader;
import io.github.rodmguerra.rodson.readers.RootReader;

public class NullReader implements JsonNullReader<Object> {

    @Override
    public Object read(RootReader r, Class<Object> clazz, JsonNull json) {
        return null;
    }
}
