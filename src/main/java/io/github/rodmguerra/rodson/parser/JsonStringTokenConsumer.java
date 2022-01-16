package io.github.rodmguerra.rodson.parser;

import io.github.rodmguerra.rodson.json.JsonString;

import java.util.List;

public class JsonStringTokenConsumer implements TokenConsumer<JsonString> {
    @Override
    public JsonString consume(List<Token> tokens) {
        Token stringToken = tokens.remove(0);
        if(Token.Type.STRING != stringToken.getType()) {
            System.out.println(stringToken);
            throw new IllegalArgumentException("String expected");
        }
        String tokenText = stringToken.getText();
        /** TODO: escapes */
        return JsonString.of(tokenText);
    }

    @Override
    public boolean canConsume(List<Token> tokens) {
        return Token.Type.STRING == tokens.get(0).getType();
    }
}
