package io.github.rodmguerra.rodson.json;

import io.github.rodmguerra.rodson.parser.JsonParser;
import io.github.rodmguerra.rodson.parser.JsonTokenConsumer;
import io.github.rodmguerra.rodson.parser.Tokenizer;

public interface Json {
    public static Json parse(String jsonString) {
        return parser().parse(jsonString);
    }

    static JsonParser parser() {
        return new JsonParser(new Tokenizer(), new JsonTokenConsumer());
    }

}
