package io.github.rodmguerra.rodson.json;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonNumber extends Number implements JsonSimpleValue  {
    private final BigDecimal number;

    private JsonNumber(BigDecimal number) {
        this.number = number;
    }

    public static JsonNumber of(int number) {
        return new JsonNumber(BigDecimal.valueOf(number));
    }

    public static JsonNumber of(long number) {
        return new JsonNumber(BigDecimal.valueOf(number));
    }

    public static JsonNumber of(float number) {
        return new JsonNumber(BigDecimal.valueOf(number));
    }

    public static JsonNumber of(double number) {
        return new JsonNumber(BigDecimal.valueOf(number));
    }

    public static JsonNumber of(BigDecimal number) {
        return new JsonNumber(number);
    }

    public static JsonNumber of(BigInteger number) {
        return new JsonNumber(new BigDecimal(number));
    }

    @Override
    public int intValue() {
        return number.intValue();
    }

    @Override
    public long longValue() {
        return number.longValue();
    }

    @Override
    public float floatValue() {
        return number.floatValue();
    }

    @Override
    public double doubleValue() {
        return number.doubleValue();
    }

    public BigInteger bigInterValue() {
        return number.toBigInteger();
    }

    public BigDecimal bigDecimalValue() {
        return number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
