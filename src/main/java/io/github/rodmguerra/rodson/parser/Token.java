package io.github.rodmguerra.rodson.parser;

import java.util.ArrayList;
import java.util.List;

public class Token {

    public static enum Type {
        BEGIN_OBJECT("{"),
        END_OBJECT("}"),
        BEGIN_ARRAY("["),
        END_ARRAY("]"),
        NULL("null"),
        NUMBER('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'),
        STRING('"'),
        BOOLEAN("true", "false"),
        COLON(":"),
        COMMA(",");

        private final List<Character> initialValues;
        private final List<String> fixedValues;

        private Type(char... initialValues) {
            this.initialValues = new ArrayList<>();
            for (char initialValue : initialValues) {
                this.initialValues.add(initialValue);
            }
            this.fixedValues = new ArrayList<>();
        }

        private Type(String... fixedValues) {
            initialValues = new ArrayList<>();
            for (int i = 0; i < fixedValues.length; i++) {
                initialValues.add(fixedValues[i].charAt(0));
            }
            this.fixedValues = new ArrayList<>();
            for (String fixedValue : fixedValues) {
                this.fixedValues.add(fixedValue);
            }
        }

        public List<Character> getInitialValues() {
            return initialValues;
        }


        public List<String> getFixedValues() {
            return fixedValues;
        }
    }

    private final Type type;
    private final String value;

    public Token(Type type, String value) {
        this.value = value;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String getText() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (type != token.type) return false;
        if (value != null ? !value.equals(token.value) : token.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
