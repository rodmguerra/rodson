package io.github.rodmguerra.rodson.json;

public class JsonNull implements JsonSimpleValue {
    public static JsonNull NULL = new JsonNull();

    private JsonNull() {
    }

    public static JsonNull parse(String json) {
        if (json.trim().equals("null")) return JsonNull.NULL;
        else throw new IllegalArgumentException(String.format("json = %s", json));
    }

    public String toString() {
        return "null";
    }

}
