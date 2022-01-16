package io.github.rodmguerra.rodson.readers.time;

import io.github.rodmguerra.rodson.json.JsonString;
import io.github.rodmguerra.rodson.readers.JsonStringReader;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.time.LocalDateTime;

public class LocalDateTimeReader implements JsonStringReader<LocalDateTime> {
    @Override
    public LocalDateTime read(RootReader r, Class<LocalDateTime> clazz, JsonString json) {
        return LocalDateTime.parse(json.stringValue());
    }
}
