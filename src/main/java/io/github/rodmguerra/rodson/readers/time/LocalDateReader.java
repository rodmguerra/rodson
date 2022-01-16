package io.github.rodmguerra.rodson.readers.time;

import io.github.rodmguerra.rodson.json.JsonString;
import io.github.rodmguerra.rodson.readers.JsonStringReader;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.time.LocalDate;

public class LocalDateReader implements JsonStringReader<LocalDate> {
    @Override
    public LocalDate read(RootReader r, Class<LocalDate> clazz, JsonString json) {
        return LocalDate.parse(json.stringValue());
    }
}
