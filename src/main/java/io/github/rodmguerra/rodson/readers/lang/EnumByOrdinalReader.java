package io.github.rodmguerra.rodson.readers.lang;

import io.github.rodmguerra.rodson.commons.ReflectionUtils;
import io.github.rodmguerra.rodson.json.JsonNumber;
import io.github.rodmguerra.rodson.readers.JsonNumberReader;
import io.github.rodmguerra.rodson.readers.RootReader;

public class EnumByOrdinalReader<E extends Enum<E>> implements JsonNumberReader<E> {
    @Override
    public E read(RootReader r, Class<E> clazz, JsonNumber json) {
        return ReflectionUtils.enumConstant(clazz, json.intValue());
    }
}
