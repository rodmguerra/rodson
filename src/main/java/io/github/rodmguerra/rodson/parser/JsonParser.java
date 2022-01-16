package io.github.rodmguerra.rodson.parser;

import io.github.rodmguerra.rodson.json.*;

import java.util.List;

public class JsonParser {
    private final Tokenizer tokenizer;
    private final TokenConsumer<Json> tokenConsumer;

    public JsonParser(Tokenizer tokenizer, TokenConsumer<Json> tokenConsumer) {
        this.tokenizer = tokenizer;
        this.tokenConsumer = tokenConsumer;
    }

    public Json parse(String value) {
        List<Token> tokens = tokenizer.tokenize(value);
        return tokenConsumer.consume(tokens);
    }

    public static void main(String[] args) {
        JsonParser parser = new JsonParser(new Tokenizer(), new JsonTokenConsumer());
        System.out.println(parser.parse("{\"name\": \"Rodrigo\"}"));
    }

    public JsonObject parseObject(String json) {
        Json obj = parse(json);
        if(!(obj instanceof JsonObject)) throw new IllegalArgumentException("Json is not an object");
        return (JsonObject) obj;
    }

    public JsonArray parseArray(String json) {
        Json obj = parse(json);
        if(!(obj instanceof JsonArray)) throw new IllegalArgumentException(String.format("Json value is not an array, json = %s", json));
        return (JsonArray) obj;
    }

    public JsonSimpleValue parseSimpleValue(String json) {
        Json obj = parse(json);
        if(!(obj instanceof JsonSimpleValue)) throw new IllegalArgumentException("Json value is not a simple");
        return (JsonSimpleValue) obj;
    }

    public JsonBoolean parseBoolean(String json) {
        Json obj = parse(json);
        if(!(obj instanceof JsonBoolean)) throw new IllegalArgumentException("Json value is not a boolean");
        return (JsonBoolean) obj;
    }

    public JsonNumber parseNumber(String json) {
        Json obj = parse(json);
        if(!(obj instanceof JsonNumber)) throw new IllegalArgumentException("Json value is not a number");
        return (JsonNumber) obj;
    }

    public JsonNull parseNull(String json) {
        Json obj = parse(json);
        if(!(obj instanceof JsonNull)) throw new IllegalArgumentException("Json value is not null");
        return (JsonNull) obj;
    }

    public JsonString parseString(String json) {
        Json obj = parse(json);
        if(!(obj instanceof JsonString)) throw new IllegalArgumentException("Json value is not a string");
        return (JsonString) obj;
    }
}
