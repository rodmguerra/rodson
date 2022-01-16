package io.github.rodmguerra.rodson.parser;

import io.github.rodmguerra.rodson.json.JsonBoolean;

import java.util.List;

public class JsonBooleanTokenConsumer implements TokenConsumer<JsonBoolean> {
    @Override
    public JsonBoolean consume(List<Token> tokens) {
        Token stringToken = tokens.remove(0);
        if(Token.Type.BOOLEAN != stringToken.getType()) throw new IllegalArgumentException("Boolean expected");
        String tokenText = stringToken.getText();
        return JsonBoolean.of(Boolean.valueOf(tokenText));
    }

    @Override
    public boolean canConsume(List<Token> tokens) {
        return Token.Type.BOOLEAN == tokens.get(0).getType();
    }
}
