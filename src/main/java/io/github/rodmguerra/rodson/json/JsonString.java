package io.github.rodmguerra.rodson.json;

public class JsonString implements JsonSimpleValue {
    private final String string;

    private JsonString(String string) {
        this.string = string;
    }

    public static JsonString of(String string) {
        return new JsonString(string);
    }

    @Override
    public String toString() {
        return "\"" +
                string
                        .replace("\\", "\\\\")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r")
                        .replace("\"", "\\\"")
                        .replace("\u0008", "\\b")
                        .replace("\u000c", "\\f")
                        .replace("\u0009", "\\t")
                + "\"";
    }

    public String stringValue() {
        return string;
    }
}
