package io.github.rodmguerra.rodson.json;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class JsonArray implements Json, Iterable<Json> {

    private final List<Json> elements;

    private JsonArray(Iterable<? extends Json> elements) {
        List<Json> list = StreamSupport.stream(elements.spliterator(), false)
                .collect(toList());
        this.elements = new ArrayList<>(list);
    }

    public static JsonArray of(Iterable<? extends Json> elements) {
        return new JsonArray(elements);
    }

    public static JsonArray of(Json... json) {
        return new JsonArray(Arrays.asList(json));
    }

    @Override
    public String toString() {
        List<String> strings = elements.stream().map(e -> e.toString()).collect(toList());
        return "[" + String.join(",", strings) + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public Json get(int i) {
        return elements.get(i);
    }

    @Override
    public Iterator<Json> iterator() {
        return new ArrayList<>(elements).iterator();
    }

    @Override
    public void forEach(Consumer<? super Json> action) {
        new ArrayList<>(elements).forEach(action);
    }

    @Override
    public Spliterator<Json> spliterator() {
        return new ArrayList<>(elements).spliterator();
    }

    public int size() {
        return elements.size();
    }

    public static class Builder {
        private List<Json> elements = new ArrayList<>();
        public Builder add(Json json) {
            elements.add(json);
            return this;
        }

        public JsonArray build() {
            return JsonArray.of(elements);
        }
    }
}
