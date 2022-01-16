package io.github.rodmguerra.rodson.parser;

import io.github.rodmguerra.rodson.json.Json;
import io.github.rodmguerra.rodson.json.JsonObject;
import io.github.rodmguerra.rodson.json.JsonString;

import java.util.*;

public class JsonObjectTokenConsumer implements TokenConsumer<JsonObject> {
    private final TokenConsumer<Json> parent;
    private final TokenConsumer<JsonString> stringConsumer;

    public JsonObjectTokenConsumer(TokenConsumer<Json> parent, TokenConsumer<JsonString> stringConsumer) {
        this.parent = parent;
        this.stringConsumer = stringConsumer;
    }

    @Override
    public JsonObject consume(List<Token> tokens) {
        Token start = tokens.remove(0);
        if(Token.Type.BEGIN_OBJECT != start.getType()) {
            throw new IllegalArgumentException("Start object expected");
        }
        Map<String,Json> properties = new LinkedHashMap<>();
        while (tokens.size() > 0) {
            Token current = tokens.remove(0);
            if(Token.Type.END_OBJECT == current.getType()) {
                return JsonObject.of(properties);
            }

            if(Token.Type.COMMA == current.getType()) {
                current = tokens.remove(0);
            }

            JsonString key = stringConsumer.consume(new ArrayList<>(Arrays.asList(current)));
            Token colon = tokens.remove(0);
            if(Token.Type.COLON != colon.getType()) {
                throw new IllegalArgumentException("Colon expected");
            }

            Json value = parent.consume(tokens);
            properties.put(key.stringValue(), value);
        }
        throw new IllegalArgumentException("End object expected");
    }

    @Override
    public boolean canConsume(List<Token> tokens) {
        return Token.Type.BEGIN_OBJECT == tokens.get(0).getType();
    }
}
