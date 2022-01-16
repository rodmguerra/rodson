package io.github.rodmguerra.rodson.readers.time;

import io.github.rodmguerra.rodson.json.JsonString;
import io.github.rodmguerra.rodson.readers.JsonStringReader;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.time.ZonedDateTime;

public class ZonedDateTimeReader implements JsonStringReader<ZonedDateTime> {
    @Override
    public ZonedDateTime read(RootReader r, Class<ZonedDateTime> clazz, JsonString json) {
        return ZonedDateTime.parse(json.stringValue());
    }
}
