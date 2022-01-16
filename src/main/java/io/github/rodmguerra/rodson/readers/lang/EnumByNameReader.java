package io.github.rodmguerra.rodson.readers.lang;

import io.github.rodmguerra.rodson.json.JsonString;
import io.github.rodmguerra.rodson.readers.JsonStringReader;
import io.github.rodmguerra.rodson.readers.RootReader;

public class EnumByNameReader<E extends Enum<E>> implements JsonStringReader<E> {
    @Override
    public E read(RootReader r, Class<E> clazz, JsonString json) {
        System.out.println(clazz + "ENUMMMMM");
        return E.valueOf(clazz,json.stringValue());
    }
}
