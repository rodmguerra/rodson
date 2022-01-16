package io.github.rodmguerra.rodson.json;

public class JsonBoolean implements Json {
    public static JsonBoolean TRUE = new JsonBoolean(true);
    public static JsonBoolean FALSE = new JsonBoolean(false);
    private final boolean bool;
    private JsonBoolean(boolean bool) {
        this.bool = bool;
    }

    public static JsonBoolean of(boolean bool) {
        return bool? JsonBoolean.TRUE : JsonBoolean.FALSE;
    }

    public String toString() {
        return bool? "true" : "false";
    }

    public boolean booleanValue() {
        return bool;
    }
}
