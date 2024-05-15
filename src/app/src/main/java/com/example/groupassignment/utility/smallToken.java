package com.example.groupassignment.utility;

import java.util.Objects;

//author of this class : jiayu jian
//reference: lab 6

public class smallToken {

    public enum Type {course,err}


    private final String token;
    private final Type type;

    public smallToken(String token, Type type) {
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
        if(type == smallToken.Type.err){
            return "err(" + token + ")";
        }else{
            return "course(" + token + ")";
        }

    }

/*    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Token)) return false;
        return this.type == ((smallToken) other).getType() && this.token.equals(((smallToken) other).getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, type);
    }*/
}
