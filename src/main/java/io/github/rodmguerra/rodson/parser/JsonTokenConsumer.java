package io.github.rodmguerra.rodson.parser;

import io.github.rodmguerra.rodson.json.Json;
import io.github.rodmguerra.rodson.json.JsonNull;
import io.github.rodmguerra.rodson.json.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonTokenConsumer implements TokenConsumer<Json>{

    private final Map<Class<? extends Json>, TokenConsumer<? extends Json>> consumerMap = new LinkedHashMap<>();

    public JsonTokenConsumer() {
        JsonStringTokenConsumer stringConsumer = new JsonStringTokenConsumer();
        consumerMap.put(JsonString.class, stringConsumer);
        consumerMap.put(JsonArray.class, new JsonArrayTokenConsumer(this));
        consumerMap.put(JsonObject.class, new JsonObjectTokenConsumer(this, stringConsumer));
        consumerMap.put(JsonNumber.class, new JsonNumberTokenConsumer());
        consumerMap.put(JsonBoolean.class, new JsonBooleanTokenConsumer());
        consumerMap.put(JsonNull.class, new JsonNullTokenConsumer());
    }

    @Override
    public Json consume(List<Token> tokens) {
        for (TokenConsumer<? extends Json> tokenConsumer : consumerMap.values()) {
            if(tokenConsumer.canConsume(tokens)) {
                //System.out.println(String.format(tokenConsumer + " can consume " + tokens));
                return tokenConsumer.consume(tokens);
            }
        }
        throw new IllegalArgumentException("Can't consume tokens: " + tokens);
    }

    @Override
    public boolean canConsume(List<Token> tokens) {
        return true;
    }
}
