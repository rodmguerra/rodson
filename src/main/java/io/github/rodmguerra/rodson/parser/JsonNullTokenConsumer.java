package io.github.rodmguerra.rodson.parser;

import io.github.rodmguerra.rodson.json.JsonNull;

import java.util.List;

public class JsonNullTokenConsumer implements TokenConsumer<JsonNull> {
    @Override
    public JsonNull consume(List<Token> tokens) {
        Token stringToken = tokens.remove(0);
        if(Token.Type.NULL != stringToken.getType()) throw new IllegalArgumentException("Boolean expected");
        return JsonNull.NULL;
    }

    @Override
    public boolean canConsume(List<Token> tokens) {
        return Token.Type.NULL == tokens.get(0).getType();
    }
}
