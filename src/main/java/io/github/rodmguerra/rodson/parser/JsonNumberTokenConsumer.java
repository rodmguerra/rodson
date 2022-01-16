package io.github.rodmguerra.rodson.parser;

import io.github.rodmguerra.rodson.json.JsonNumber;

import java.math.BigDecimal;
import java.util.List;

public class JsonNumberTokenConsumer implements TokenConsumer<JsonNumber> {
    @Override
    public JsonNumber consume(List<Token> tokens) {
        Token stringToken = tokens.remove(0);
        if(Token.Type.NUMBER != stringToken.getType()) throw new IllegalArgumentException("String expected");
        String tokenText = stringToken.getText();
        return JsonNumber.of(new BigDecimal(tokenText));
    }

    @Override
    public boolean canConsume(List<Token> tokens) {
        return Token.Type.NUMBER == tokens.get(0).getType();
    }
}
