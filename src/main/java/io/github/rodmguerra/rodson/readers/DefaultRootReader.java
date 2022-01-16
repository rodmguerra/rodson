package io.github.rodmguerra.rodson.readers;

import io.github.rodmguerra.rodson.commons.Type;
import io.github.rodmguerra.rodson.readers.time.LocalDateReader;
import io.github.rodmguerra.rodson.readers.time.LocalTimeReader;
import io.github.rodmguerra.rodson.json.*;
import io.github.rodmguerra.rodson.readers.lang.*;
import io.github.rodmguerra.rodson.readers.util.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static io.github.rodmguerra.rodson.commons.ReflectionUtils.wrap;

public class DefaultRootReader implements RootReader {
    private final JsonObjectReader defaultObjectReader = new ObjectSetterReader(false);
    private final TypedListJsonArrayReader listReader = new ListReader();
    private final TypedSetJsonArrayReader setReader = new SetReader();
    private final MapReader mapReader = new MapReader();
    private final TypedJsonArrayReader javaArrayReader = new JavaArrayReader();
    private final JsonNullReader<Object> defaultNullReader = new NullReader();
    private final JsonNumberReader<Number> defaultNumberReader = new NumberReader();
    private final JsonStringReader<Enum> enumByNameReader = new EnumByNameReader();
    private final Map<Class, JsonMultiReader> multiReaders;
    private final Map<Class, JsonObjectReader> objectReaders;
    private final Map<Class, JsonStringReader> stringReaders;
    private final Map<Class, JsonArrayReader> arrayReaders;
    private final Map<Class, JsonBooleanReader> booleanReaders;
    private final Map<Class, JsonNullReader> nullReaders;
    private final Map<Class, JsonNumberReader> numberReaders;
    private final JsonNumberReader<Enum> enumByOrdinalReader = new EnumByOrdinalReader();

    public DefaultRootReader(Map<Class, JsonMultiReader> multiReaders, Map<Class, JsonObjectReader> objectReaders, Map<Class, JsonStringReader> stringReaders, Map<Class, JsonArrayReader> arrayReaders, Map<Class, JsonBooleanReader> booleanReaders, Map<Class, JsonNullReader> nullReaders, Map<Class, JsonNumberReader> numberReaders) {
        this.multiReaders = multiReaders;
        this.objectReaders = objectReaders;
        this.stringReaders = stringReaders;
        this.arrayReaders = arrayReaders;
        this.booleanReaders = booleanReaders;
        this.nullReaders = nullReaders;
        this.numberReaders = numberReaders;
    }

    public static DefaultRootReader.Builder builder() {
        return new Builder();
    }

    public static DefaultRootReader build() {
        return new Builder().build();
    }

    @Override
    public <T> T read(Class<T> clazz, Json json) {
        return (T) read(Type.of(clazz), json);
    }

    public <T> T read(Type<T> type, Json json) {
        Class clazz = type.getRawClass();
        return (T) read(clazz, json, multiReaders, () -> {
            if (json instanceof JsonObject) return readObject(type, (JsonObject) json);
            if (json instanceof JsonArray) return readArray(type, (JsonArray) json);
            if (json instanceof JsonString) return readString(clazz, (JsonString) json);
            if (json instanceof JsonNumber) return readNumber(clazz, (JsonNumber) json);
            if (json instanceof JsonBoolean) return readBoolean(clazz, (JsonBoolean) json);
            if (json instanceof JsonNull) return readNull(clazz, (JsonNull) json);
            throw new UnsupportedOperationException("No json reader for class: " + clazz.getName());
        });
    }

    private <T> T readBoolean(Class<T> clazz, JsonBoolean json) {
        return (T) read(clazz, json, booleanReaders, () -> {
            throw new UnsupportedOperationException();
        });
    }

    private <T> T readNumber(Class<T> clazz, JsonNumber json) {
        return (T) read(clazz, json, numberReaders, () -> {
            if (Number.class.isAssignableFrom(wrap(clazz)))
                return (T) defaultNumberReader.read(this, (Class<Number>) clazz, json);
            if (clazz.isEnum())
                return (T) enumByOrdinalReader.read(this, (Class<Enum>) clazz, json);
            throw new UnsupportedOperationException(String.format("Can't read JSON number as %s, json=%s", clazz.getName(), json));
        });
    }

    private <T> T readNull(Class<T> clazz, JsonNull json) {
        return (T) read(clazz, json, nullReaders, () -> (T) defaultNullReader.read(this, Object.class, json));
    }

    private <T> T readString(Class<T> clazz, JsonString json) {
        return (T) read(clazz, json, stringReaders, () -> {
            if (clazz.isEnum()) {
                return (T) enumByNameReader.read(this, (Class<Enum>) clazz, json);
            }
            throw new UnsupportedOperationException(String.format("Can't read JSON string as %s, json=%s", clazz.getName(), json));
        });
    }


    private <T> T readObject(Type type, JsonObject json) {
        Class clazz = type.getRawClass();
        return (T) read(clazz, json, objectReaders, () -> {
            if (clazz.isAssignableFrom(Map.class)) {
                List<Type> generics = type.getGenericParams();
                Class keyClass = generics.size() > 0? generics.get(0).getRawClass() : Object.class;
                Class valueClass = generics.size() > 1? generics.get(1).getRawClass() : Object.class;
                return (T) readMapOf(keyClass, valueClass, json);
            }
            return (T) defaultObjectReader.read(this, clazz, json);
        });
    }


    private <T, J extends Json, R extends JsonReader<T, J>> T read(Class<T> clazz, J json, Map<Class, R> readers, Supplier<T> fallback) {
        R reader = findReaderInMap(clazz, readers);
        if (reader == null && fallback != null) return fallback.get();
        return reader.read(this, clazz, json);
    }

    private Object readArray(Type type, JsonArray json) {
        Class clazz = type.getRawClass();
        return read(clazz, json, (Map) arrayReaders, () -> {
            if (clazz.isArray()) return readArrayOf(clazz.getComponentType(), json);
            Class elementType = Object.class;
            List<Type> generics = type.getGenericParams();
            if (generics.size() > 0) elementType = generics.get(0).getRawClass();
            if (clazz.isAssignableFrom(List.class)) return readListOf(elementType, json);
            if (clazz.isAssignableFrom(Set.class)) return readSetOf(elementType, json);
            throw new UnsupportedOperationException(String.format("Can't read JSON array as %s", clazz.getName()));
        });
    }

    private <R> R findReaderInMap(Class<?> clazz, Map<Class, R> map) {
        R reader = map.get(clazz);
        if (reader == null) {
            Class subclass = findSubclass((Class) clazz, (Set) map.keySet());
            if (subclass != null) reader = map.get(subclass);
        }
        return reader;
    }

    private Class findSubclass(Class clazz, Iterable<Class> classes) {
        for (Class aClass : classes) {
            if (clazz.isAssignableFrom(aClass)) return aClass;
        }
        return null;
    }

    @Override
    public <K,V> Map<K,V> readMapOf(Class<K> keyType, Class<V> valueType, JsonObject json) {
        return mapReader.readMapOf(Type.of(keyType), Type.of(valueType), this, json);
    }

    @Override
    public <K, V> Map<K, V> readMapOf(Type<K> keyType, Type<V> valueType, JsonObject json) {
        return mapReader.readMapOf(keyType, valueType, this, json);
    }

    @Override
    public <T> List<T> readListOf(Class<T> elementType, JsonArray json) {
        return listReader.readIterableOf(elementType, this, json);
    }

    @Override
    public <T> Set<T> readSetOf(Class<T> elementType, JsonArray json) {
        return setReader.readIterableOf(elementType, this, json);
    }

    @Override
    public <T> T[] readArrayOf(Class<T> elementType, JsonArray json) {
        return javaArrayReader.readArrayOf(elementType, this, json);
    }

    public static class Builder {
        private final Map<Class, JsonMultiReader> multiReaders = new LinkedHashMap<>();
        private Map<Class, JsonObjectReader> objectReaders = new LinkedHashMap<>();
        private Map<Class, TypedIterableJsonArrayReader> iterableReaders = new LinkedHashMap<>();
        private Map<Class, JsonArrayReader> arrayReaders = new LinkedHashMap<>();
        private Map<Class, JsonStringReader> stringReaders = new LinkedHashMap<>();
        private Map<Class, JsonBooleanReader> booleanReaders = new LinkedHashMap<>();
        private Map<Class, JsonNullReader> nullReaders = new LinkedHashMap<>();
        private Map<Class, JsonNumberReader> numberReaders = new LinkedHashMap<>();

        public Builder() {
            this.stringReaders.put(String.class, new StringReader());
            this.stringReaders.put(LocalDate.class, new LocalDateReader());
            this.stringReaders.put(LocalTime.class, new LocalTimeReader());
            this.booleanReaders.put(Boolean.class, new BooleanReader());
            this.numberReaders.put(Number.class, new NumberReader());
        }

        <T> Builder withReader(Class<? extends T> type, JsonObjectReader<?> reader) {
            objectReaders.put(type, reader);
            return this;
        }


        <T> Builder withArrayReader(Class<? extends T> elementType, JsonArrayReader<T> reader) {
            arrayReaders.put(elementType, reader);
            return this;
        }

        public DefaultRootReader build() {
            return new DefaultRootReader(multiReaders, objectReaders, stringReaders, arrayReaders, booleanReaders, nullReaders, numberReaders);
        }

    }

}
