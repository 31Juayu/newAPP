package com.example.groupassignment;

import com.example.groupassignment.utility.RedBlackTree;
import com.example.groupassignment.utility.Token;

import java.util.ArrayList;

/**
 * @autor Ruize Luo u7776709
 */
public class TokenizerForTest {
    private String buffer;
    private Token currentToken;

    public TokenizerForTest(String text) {
        buffer = text;          // Save input text (string)
        next();                 // Extract the first token.
    }

    /**
     * Removes the first comma from a given string.
     *
     * @param input the string to process
     * @return the string with the first comma removed
     */
    public static String removeFirstComma(String input) {
        return input.replaceFirst(",", "");
    }

    /**
     * Extracts the next token from the buffer and updates the current token.
     */
    public void next() {
        boolean ifNum = true;

        buffer = buffer.trim(); // Remove whitespace

        if (buffer.isEmpty()) {
            currentToken = null; // If there's no string left, set currentToken to null and return
            return;
        }

        ArrayList<String> list = splitString(buffer);
        String firstString = list.get(0);
        firstString = firstString.toLowerCase();

        // Check if the first string is a number
        try {
            Integer.parseInt(firstString);
        } catch (NumberFormatException e) {
            ifNum = false;
        }

        // Create a Red-Black Tree to store country names
        RedBlackTree<String> treeCountry = new RedBlackTree<>();
        treeCountry.put("china", "china");

        // Determine the token type and create the token
        if (ifNum) {
            currentToken = new Token(firstString, Token.Type.year);
        } else if (treeCountry.search(firstString) != null) {
            currentToken = new Token(firstString, Token.Type.country);
        } else if (firstString.equals("quality")) {
            currentToken = new Token(firstString, Token.Type.quality);
        } else if (firstString.equals("*")) {
            currentToken = new Token(firstString, Token.Type.asterisk);
        } else {
            currentToken = new Token(firstString, Token.Type.info);
        }

        // Update the buffer by removing the processed token
        String newBuffer = removeFirstComma(buffer);
        int tokenLen = currentToken.getToken().length();
        buffer = newBuffer.substring(tokenLen);
    }

    /**
     * Splits a given string into a list of strings using commas as delimiters.
     *
     * @param input the string to split
     * @return a list of strings
     */
    public static ArrayList<String> splitString(String input) {
        ArrayList<String> words = new ArrayList<>();
        String[] tokens = input.split(",");
        for (String token : tokens) {
            words.add(token.trim());
        }
        return words;
    }

    /**
     * Returns the current token.
     *
     * @return the current token
     */
    public Token current() {
        return currentToken;
    }

    /**
     * Checks if there are more tokens to process.
     *
     * @return true if there are more tokens, false otherwise
     */
    public boolean hasNext() {
        return currentToken != null;
    }
}
