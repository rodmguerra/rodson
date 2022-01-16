package io.github.rodmguerra.rodson.readers.lang;

import io.github.rodmguerra.rodson.json.JsonString;
import io.github.rodmguerra.rodson.readers.JsonStringReader;
import io.github.rodmguerra.rodson.readers.RootReader;

public class StringReader implements JsonStringReader<String> {
    @Override
    public String read(RootReader r, Class<String> clazz, JsonString json) {
        return json.stringValue();
    }
}
