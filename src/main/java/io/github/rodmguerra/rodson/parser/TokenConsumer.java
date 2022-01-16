package io.github.rodmguerra.rodson.parser;

import io.github.rodmguerra.rodson.json.Json;

import java.util.List;

public interface TokenConsumer<T extends Json> {
    T consume(List<Token> tokens);
    boolean canConsume(List<Token> tokens);

}
