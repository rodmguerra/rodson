package io.github.rodmguerra.rodson;

import io.github.rodmguerra.rodson.commons.Type;
import io.github.rodmguerra.rodson.json.*;
import io.github.rodmguerra.rodson.parser.JsonParser;
import io.github.rodmguerra.rodson.parser.JsonTokenConsumer;
import io.github.rodmguerra.rodson.parser.Tokenizer;
import io.github.rodmguerra.rodson.readers.DefaultRootReader;
import io.github.rodmguerra.rodson.readers.RootReader;
import io.github.rodmguerra.rodson.writers.RootWriter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Rodson implements RootReader, RootWriter {
    private final RootReader reader;
    private final RootWriter writer;
    private final JsonParser parser;

    public Rodson(RootWriter writer, RootReader reader, JsonParser parser) {
        this.writer = writer;
        this.reader = reader;
        this.parser = parser;
    }

    @Override
    public <T> T read(Type<T> type, Json json) {
        return reader.read(type, json);
    }

    public <T> T read(Type<T> type, String json) {
        return reader.read(type, parser.parse(json));
    }

    @Override
    public <T> T read(Class<T> clazz, Json json) {
        return reader.read(clazz, json);
    }

    public <T> T read(Class<T> clazz, String json) {
        return reader.read(clazz, parser.parse(json));
    }

    public Json parse(String json) {
        return parser.parse(json);
    }

    public JsonObject parseObject(String json) {
        return parser.parseObject(json);
    }

    public JsonSimpleValue parseSimpleValue(String json) {
        return parser.parseSimpleValue(json);
    }

    public JsonString parseString(String json) {
        return parser.parseString(json);
    }

    public JsonBoolean parseBoolean(String json) {
        return parser.parseBoolean(json);
    }

    public JsonArray parseArray(String json) {
        return parser.parseArray(json);
    }

    public JsonNull parseNull(String json) {
        return parser.parseNull(json);
    }

    public JsonNumber parseNumber(String json) {
        return parser.parseNumber(json);
    }

    @Override
    public <K, V> Map<K, V> readMapOf(Class<K> keyType, Class<V> valueType, JsonObject json) {
        return reader.readMapOf(keyType, valueType, json);
    }

    public <K, V> Map<K, V> readMapOf(Type<K> keyType, Type<V> valueType, JsonObject json) {
        return reader.readMapOf(keyType, valueType, json);
    }
    public <K, V> Map<K, V> readMapOf(Class<K> keyClass, Class<V> valueClass, String json) {
        return reader.readMapOf(keyClass, valueClass, parser.parseObject(json));
    }
    public <K, V> Map<K, V> readMapOf(Type<K> keyType, Type<V> valueType, String json) {
        return reader.readMapOf(keyType, valueType, parser.parseObject(json));
    }


    @Override
    public <T> List<T> readListOf(Class<T> elementClass, JsonArray json) {
        return reader.readListOf(elementClass, json);
    }

    public <T> List<T> readListOf(Class<T> elementClass, String json) {
        return reader.readListOf(elementClass, parser.parseArray(json));
    }

    @Override
    public <T> Set<T> readSetOf(Class<T> elementClass, JsonArray json) {
        return reader.readSetOf(elementClass, json);
    }

    public <T> Set<T> readSetOf(Class<T> elementClass, String json) {
        return reader.readSetOf(elementClass, parser.parseArray(json));
    }

    @Override
    public <T> T[] readArrayOf(Class<T> elementClass, JsonArray json) {
        return reader.readArrayOf(elementClass, json);
    }

    public <T> T[] readArrayOf(Class<T> elementClass, String json) {
        return reader.readArrayOf(elementClass, parser.parseArray(json));
    }

    @Override
    public Json write(Object object) {
        return writer.write(object);
    }

    public static class User {
        private String nome;
        private Integer idade;
        private LocalDate nascimento;
        private Gender gender;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public int getIdade() {
            return idade;
        }

        public void setIdade(Integer idade) {
            this.idade = idade;
        }

        public LocalDate getNascimento() {
            return nascimento;
        }

        public void setNascimento(LocalDate nascimento) {
            this.nascimento = nascimento;
        }

        public Gender getGender() {
            return gender;
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }


        @Override
        public String toString() {
            return nome;
        }

        private static enum  Gender {MALE, FEMALE};

        public static class School {
            private List<User> students;

            public List<User> getStudents() {
                return students;
            }

            public void setStudents(List<User> students) {
                this.students = students;
            }

            @Override
            public String toString() {
                return "School{" +
                        "students=" + students +
                        '}';
            }
        }


    }

    public static void main(String[] args) {

        Rodson rodson = new Rodson(null, DefaultRootReader.build(), new JsonParser(new Tokenizer(), new JsonTokenConsumer()));
        //System.out.println(rodson.read("{\"idiot\":[{\"nome\" : \"Rodrigo\" , \"idade\": 34}, [2, 4, 7], {}]}", Object.class));
        //List<User> users = new ArrayList<>();

        //System.out.println();
        String json = "{\"students\" : [{\"nome\" : \"\\nRodrigo\" , \"idade\":34, \"nascimento\": \"1987-03-12\", \"gender\":0}, {\"nome\" : \"Alice\" , \"idade\":9, \"nascimento\": \"2012-06-21\", \"gender\":1}]}";
        Type<Map<String,List<User>>> type = new Type<Map<String, List<User>>>() {};

        Map<String, List<User>> map = rodson.read(type, json);

        System.out.println(map);
        User.School school = rodson.read(new Type<User.School>() {
        }, json);

        System.out.println(school);

        Map<String, User[]> read = rodson.read(new Type<Map<String, User[]>>() {
        }, json);
        for (String s : read.keySet()) {
            System.out.println(read.get(s));
        }

        json = "{\"students\":[{\"nome\":\"\\nRo  \\ud852\\udf62  drigo\",\"idade\":34,\"nascimento\":\"1987-03-12\",\"gender\":0},{\"nome\":\"Alice\",\"idade\":9,\"nascimento\":\"2012-06-21\",\"gender\":1}]}";
        System.out.println(json);
        System.out.println(rodson.parse(json));
    }

}
