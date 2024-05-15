package com.example.groupassignment;

import com.example.groupassignment.utility.Token;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * @author Ruize Luo u7776709
 *
 */
public class Parser_2ForTest implements myParserForTest {

    TokenizerForTest tokenizer;
    ArrayList<Token> parsedList;

    public Parser_2ForTest(TokenizerForTest tokenizer) {
        this.tokenizer = tokenizer;
        parsedList = new ArrayList<>();
    }

    /**
     * Adheres to the grammar rule:
     * <S> ::= d<R> | <R>
     *
     * @return type: Exp.
     */

    // The return value is the result of the parse
    // Default return is the right side
    // Since no calculations are performed, all parsing methods return a boolean indicating if each step holds.
    public boolean parseExp() {
        if (tokenizer.current().getType() == Token.Type.quality) {
            parsedList.add(tokenizer.current());
            tokenizer.next(); // Consumes the 'd' terminal
            return parseR(); // Parses the <R> part
        } else {
            return parseR(); // Parses the <R> part
        }
    }

    /**
     * Adheres to the grammar rule:
     * R → cM | *N
     *
     * @return type: Exp.
     */
    public boolean parseR() {
        if (tokenizer.current().getType() == Token.Type.country) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseM();
        } else if (tokenizer.current().getType() == Token.Type.asterisk) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseN();
        } else {
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * M → *F
     *
     * @return type: Exp.
     */
    public boolean parseM() {
        if (tokenizer.current().getType() == Token.Type.asterisk) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseFinal();
        } else {
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * N → cF
     *
     * @return type: Exp.
     */
    public boolean parseN() {
        if (tokenizer.current().getType() == Token.Type.country) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseFinal();
        } else {
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * <R> ::=  null | d
     *
     * @return type: Exp.
     */
    public boolean parseFinal() {
        if (!tokenizer.hasNext()) {
            return true;
        } else if (tokenizer.current().getType() == Token.Type.quality) {
            parsedList.add(tokenizer.current());
            tokenizer.next(); // Consumes the 'd' terminal
            if (!tokenizer.hasNext()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public ArrayList<Token> getRes(){
        return parsedList;
    }
}
