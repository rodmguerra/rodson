package io.github.rodmguerra.rodson.parser;

import io.github.rodmguerra.rodson.json.Json;
import io.github.rodmguerra.rodson.json.JsonArray;

import java.util.ArrayList;
import java.util.List;

public class JsonArrayTokenConsumer implements TokenConsumer<JsonArray> {
    private final JsonTokenConsumer parent;

    public JsonArrayTokenConsumer(JsonTokenConsumer parent) {
        this.parent = parent;
    }

    @Override
    public JsonArray consume(List<Token> tokens) {
        Token start = tokens.remove(0);
        if(Token.Type.BEGIN_ARRAY != start.getType()) {
            throw new IllegalArgumentException("Begin array expected");
        }
        List<Json> elements = new ArrayList<>();
        while (tokens.size() > 0) {
            Token current = tokens.get(0);
            if(Token.Type.END_ARRAY == current.getType()) {
                tokens.remove(0);
                return JsonArray.of(elements);
            }

            if(Token.Type.COMMA == current.getType()) {
                tokens.remove(0);
            }

            Json value = parent.consume(tokens);
            elements.add(value);
        }
        throw new IllegalArgumentException("End array expected");
    }

    @Override
    public boolean canConsume(List<Token> tokens) {
        return Token.Type.BEGIN_ARRAY == tokens.get(0).getType();
    }
}
