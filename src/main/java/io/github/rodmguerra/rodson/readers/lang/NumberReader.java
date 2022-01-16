package io.github.rodmguerra.rodson.readers.lang;

import io.github.rodmguerra.rodson.json.JsonNumber;
import io.github.rodmguerra.rodson.readers.JsonNumberReader;
import io.github.rodmguerra.rodson.readers.RootReader;

import java.math.BigDecimal;
import java.math.BigInteger;

import static io.github.rodmguerra.rodson.commons.ReflectionUtils.wrap;

public class NumberReader<N extends Number> implements JsonNumberReader<N> {

    @Override
    public N read(RootReader r, Class<N> clazz, JsonNumber json) {
        clazz = wrap(clazz);
        if (clazz.isAssignableFrom(BigDecimal.class)) return (N) json.bigDecimalValue();
        if (clazz.isAssignableFrom(Byte.class)) return (N) Byte.valueOf(json.byteValue());
        if (clazz.isAssignableFrom(Short.class)) return (N)  Short.valueOf(json.shortValue());
        if (clazz.isAssignableFrom(Integer.class)) return (N) Integer.valueOf(json.intValue());
        if (clazz.isAssignableFrom(Long.class)) return (N)  Long.valueOf(json.longValue());
        if (clazz.isAssignableFrom(Float.class)) return (N) Float.valueOf(json.floatValue());
        if (clazz.isAssignableFrom(Double.class)) return (N) Double.valueOf(json.doubleValue());
        if (clazz.isAssignableFrom(BigInteger.class)) return (N) json.bigInterValue();
        if (clazz.isAssignableFrom(JsonNumber.class)) return (N) json;
        throw new IllegalArgumentException("Can't generate instance of number for type " + clazz );
    }
}
