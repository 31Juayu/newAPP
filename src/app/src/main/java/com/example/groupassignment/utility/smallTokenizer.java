package com.example.groupassignment.utility;

import java.util.ArrayList;

public class smallTokenizer {
    private String buffer;
    private smallToken currentToken;


    public static boolean isBlank(String str) {
        // 使用trim()方法删除开头和结尾的空白字符，然后检查长度是否为0
        return str.trim().isEmpty();
    }

    public smallTokenizer(String text) {
        buffer = text;
        next();
    }

    public static String removeFirstComma(String input) {
        return input.replaceFirst(",", "");
    }

    public void next() {
        buffer = buffer.trim();     // remove whitespace

        if (buffer.isEmpty()) {
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }

        ArrayList<String> list  = splitString(buffer);
        String firstString = list.get(0);
        firstString = firstString.toLowerCase();
        if(isBlank(firstString)){
            currentToken = new smallToken(firstString,smallToken.Type.err);
        }else{
            currentToken = new smallToken(firstString,smallToken.Type.course);
        }

        String newBuffer = removeFirstComma(buffer);
        int tokenLen = currentToken.getToken().length();
        buffer = newBuffer.substring(tokenLen);
    }

    public static ArrayList<String> splitString(String input) {
        ArrayList<String> words = new ArrayList<>();
        String[] tokens = input.split(",");
        for (String token : tokens) {
            words.add(token.trim());
        }
        return words;
    }

    public smallToken current() {
        return currentToken;
    }

    public boolean hasNext() {
        return currentToken != null;
    }
}
