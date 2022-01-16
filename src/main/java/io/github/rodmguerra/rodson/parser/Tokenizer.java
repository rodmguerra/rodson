package io.github.rodmguerra.rodson.parser;

import io.github.rodmguerra.rodson.commons.Tuple2;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {

    public List<Token> tokenize(String json) {
        List<Token> tokens = new ArrayList<>();
        String remaining = json;
        while (true) {
            if (remaining.isEmpty()) break;
            Tuple2<Token, String> tuple = nextToken(remaining);
            Token currentToken = tuple.get_1();
            if (currentToken == null) break;
            tokens.add(currentToken);
            remaining = tuple.get_2();
        }
        return tokens;
    }

    private Tuple2<Token, String> nextToken(String json) {
        json = json.trim();
        for (Token.Type tokenType : Token.Type.values()) {
            for (char value : tokenType.getInitialValues()) {
                if (json.charAt(0) == value) {
                    Tuple2<Token, String> parse = nextToken(tokenType, json);
                    return parse;
                }
            }
        }
        throw new IllegalArgumentException(String.format("Invalid token %s", json));
    }

    private static Tuple2<Token, String> nextToken(Token.Type type, String json) {
        json = json.trim();

        if (type == Token.Type.NUMBER) {
            return nextNumberToken(json);
        }

        if (type == Token.Type.STRING) {
            return nextStringToken(json);
        }

        return nextSimpleToken(type, json);
    }

    private static Tuple2<Token, String> nextSimpleToken(Token.Type type, String json) {
        for (String fixedValue : type.getFixedValues()) {
            if (json.startsWith(fixedValue)) {
                return new Tuple2<>(new Token(type, fixedValue), json.substring(fixedValue.length()));
            }
        }
        return null;
    }

    private static Tuple2<Token, String> nextStringToken(String json) {
        boolean start = true;
        boolean escape = false;
        int i;
        String text = "";
        for (i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (start && c == '"') {
                start = false;
                continue;
            }
            if (!start && c == '"') {
                break;
            }
            if (!escape && c == '\\') {
                escape = true;
                continue;
            }
            if (c >= 0x0000 && c <= 0x001f)
                throw new IllegalArgumentException(String.format("Invalid character inside string, char = %s", "\\u" + lpad(Integer.toHexString(c), 4)));
            if (escape) {
                if (Arrays.asList('\\', '/', 'b', 'f', 'n', 'r', 't').contains(c)) {
                    escape = false;
                    if (c == '\"') text += '"';
                    if (c == '\\') text += '\\';
                    if (c == '/') text += '\u002f';
                    if (c == 'b') text += '\u0008';
                    if (c == 'f') text += '\u000c';
                    if (c == 'n') text += '\n';
                    if (c == 'r') text += '\r';
                    if (c == 't') text += '\u0009';
                    escape = false;
                    continue;
                }
                if (c == 'u') {
                    String substring = json.substring(i);
                    System.out.println(substring);
                    //u4545
                    if (substring.matches("^u[0-9a-fA-F]{4}\\\\u[0-9a-fA-F]{4}.*")) {
                        String int1 = "" + json.charAt(++i) + json.charAt(++i) + json.charAt(++i) + json.charAt(++i) ;
                        System.out.println(int1);
                        i+=2;
                        String int2 = "" + json.charAt(++i) + json.charAt(++i) + json.charAt(++i) + json.charAt(++i) ;
                        System.out.println(int2);

                        int i1 = Integer.parseInt(int2, 16);
                        int i2 = Integer.parseInt(int2, 16);
                        int utf16 = (i1 << 16) | i2;

                        text += (char) utf16;
                        escape = false;
                        continue;
                    }
                    if (substring.matches("^u[0-9a-fA-F]{4}.*")) {
                        String unicode = "" + json.charAt(++i) + json.charAt(++i) + json.charAt(++i) + json.charAt(++i);
                        System.out.println(unicode);
                        text += (char) Integer.parseInt(unicode, 16);
                        escape = false;
                        continue;
                    } else
                        throw new IllegalArgumentException(String.format("Invalid unicode mask %s", "\\" + substring));
                }
            }
            text += c;
        }
        return new Tuple2<>(new Token(Token.Type.STRING, text), json.substring(i + 1));
    }

    private static Tuple2<Token, String> nextNumberToken(String json) {
        boolean start = true;
        boolean exp = false;
        boolean expStart = false;
        boolean frac = false;

        int i;
        for (i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (Character.isDigit(c)) {
                start = false;
                expStart = false;
                continue;
            }
            if (start && c == '-') {
                start = false;
                continue;
            }
            if (expStart && c == '-') {
                expStart = false;
                continue;
            }
            if (!start && !exp && !frac && c == '.') {
                frac = true;
                continue;
            }
            if (!start && !exp && (c == 'e' || c == 'E')) {
                exp = true;
                expStart = true;
                continue;
            }
            break;
        }
        return new Tuple2<>(new Token(Token.Type.NUMBER, json.substring(0, i)), json.substring(i));
    }

    public static void main(String[] args) {
        System.out.println(new Tokenizer().tokenize("\" a\\\\nb        \""));
    }

    private static String lpad(String inputString, int length) {
        return String.format("%1$" + length + "s", inputString).replace(' ', '0');
    }
}