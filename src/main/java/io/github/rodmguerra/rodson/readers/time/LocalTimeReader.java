package io.github.rodmguerra.rodson.readers.time;

import io.github.rodmguerra.rodson.json.JsonString;
import io.github.rodmguerra.rodson.readers.JsonStringReader;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.time.LocalTime;

public class LocalTimeReader implements JsonStringReader<LocalTime> {
    @Override
    public LocalTime read(RootReader r, Class<LocalTime> clazz, JsonString json) {
        return LocalTime.parse(json.stringValue());
    }
}
