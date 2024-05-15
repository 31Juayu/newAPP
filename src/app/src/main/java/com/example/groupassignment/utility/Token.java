package com.example.groupassignment.utility;
import java.util.Objects;

//author of this class : jiayu jian
//reference: lab 6

public class Token {

    public enum Type {info, country, year, quality,asterisk}


    public static class IllegalTokenException extends IllegalArgumentException {
        public IllegalTokenException(String errorMessage) {
            super(errorMessage);
        }
    }

    private final String token;
    private final Type type;

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type == Type.info) {
            return "info(" + token + ")";
        } else if (type == Type.country) {
            return "country(" + token + ")";
        } else if (type == Type.year) {
            return "year(" + token + ")";
        } else if (type == Type.asterisk) {
            return "asterisk(" + token + ")";
        } else {
            return "quality(" + token + ")";
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Token)) return false;
        return this.type == ((Token) other).getType() && this.token.equals(((Token) other).getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, type);
    }
}
