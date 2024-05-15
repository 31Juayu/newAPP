package com.example.groupassignment.utility;
import android.content.Context;

import java.util.ArrayList;
import java.util.Scanner;

//author of this class : jiayu jian
//reference: lab 6
public class Tokenizer {
    private String buffer;
    private Token currentToken;
    //private RedBlackTree<String> treeCountry;

    Context context;


    public Tokenizer(String text, Context context) {
        buffer = text;          // save input text (string)
        this.context = context;
        //treeCountry = usefulMethod.readCSVLinesAll("CLTEARCHIVE_COUNTRY.csv");
        next();                 // extracts the first token.
    }

    public static String removeFirstComma(String input) {
        return input.replaceFirst(",", "");
    }

    public void next() {
        boolean ifNum = true;

        buffer = buffer.trim();     // remove whitespace

        if (buffer.isEmpty()) {
            currentToken = null;    // if there's no string left, set currentToken null and return
            return;
        }

        ArrayList<String> list  = splitString(buffer);
        String firstString = list.get(0);
        firstString = firstString.toLowerCase();
        try {
            Integer.parseInt(firstString);

        } catch (NumberFormatException e) {
            ifNum = false;
        }

        RedBlackTree<String> treeCountry = usefulMethod.readCSVLinesAllCountry(context);

        if(ifNum){
            currentToken = new Token(firstString,Token.Type.year);
        } else if (treeCountry.search(firstString) != null) {
            currentToken = new Token(firstString,Token.Type.country);
        } else if (firstString.equals("quality")) {
            //这里直接是quality,后续再处理
            currentToken = new Token(firstString,Token.Type.quality);
        } else if (firstString.equals("*")) {
            //这里直接是*,后续再处理
            currentToken = new Token(firstString,Token.Type.asterisk);
        }else {
            currentToken = new Token(firstString,Token.Type.info);
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

    public Token current() {
        return currentToken;
    }

    public boolean hasNext() {
        return currentToken != null;
    }

/*    public Tokenizer deepCopy() {

        String copiedBuffer = new String(this.buffer);

        Token copiedCurrentToken = null;
        if (this.currentToken != null) {
            copiedCurrentToken = new Token(this.currentToken.getToken(), this.currentToken.getType());
        }

        Tokenizer copiedTokenizer = new Tokenizer(copiedBuffer);
        copiedTokenizer.currentToken = copiedCurrentToken;

        return copiedTokenizer;
    }*/

}
