package io.github.rodmguerra.rodson.readers.time;

import io.github.rodmguerra.rodson.json.JsonString;
import io.github.rodmguerra.rodson.readers.JsonStringReader;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.time.OffsetDateTime;

public class OffsetDateTimeReader implements JsonStringReader<OffsetDateTime> {
    @Override
    public OffsetDateTime read(RootReader r, Class<OffsetDateTime> clazz, JsonString json) {
        return OffsetDateTime.parse(json.stringValue());
    }
}
