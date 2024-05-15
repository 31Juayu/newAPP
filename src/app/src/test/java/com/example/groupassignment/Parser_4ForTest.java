package com.example.groupassignment;

import com.example.groupassignment.utility.Token;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * @author Ruize Luo u7776709
 *
 */
public class Parser_4ForTest implements myParserForTest {

    TokenizerForTest tokenizer;
    ArrayList<Token> parsedList;

    public Parser_4ForTest(TokenizerForTest tokenizer) {
        this.tokenizer = tokenizer;
        parsedList = new ArrayList<>();
    }

    /**
     * Adheres to the grammar rule:
     * <S> ::= d<R> | <R>
     *
     * @return type: Exp.
     */

    // The return is the result of the parsing
    // Defaults to returning the right side
    // As no calculations are performed, all parsing methods are boolean to check if each step is correct.
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
     * R → aM | bN | *H
     *
     * @return type: Exp.
     */
    public boolean parseR() {
        if (tokenizer.current().getType() == Token.Type.year) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseM();
        } else if (tokenizer.current().getType() == Token.Type.info) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseN();
        } else if (tokenizer.current().getType() == Token.Type.asterisk) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseH();
        } else {
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * M → bL | *W
     *
     * @return type: Exp.
     */
    public boolean parseM() {
        if (tokenizer.current().getType() == Token.Type.info) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseL();
        } else if (tokenizer.current().getType() == Token.Type.asterisk) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseW();
        } else {
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * N → aX | *Y
     *
     * @return type: Exp.
     */
    public boolean parseN() {
        if (tokenizer.current().getType() == Token.Type.year) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseX();
        } else if (tokenizer.current().getType() == Token.Type.asterisk) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseY();
        } else {
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * H → aP | bQ
     *
     * @return type: Exp.
     */
    public boolean parseH() {
        if (tokenizer.current().getType() == Token.Type.year) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseP();
        } else if (tokenizer.current().getType() == Token.Type.info) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseQ();
        } else {
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * L → *E
     *
     * @return type: Exp.
     */
    public boolean parseL() {
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
     * W → bF
     *
     * @return type: Exp.
     */
    public boolean parseW() {
        if (tokenizer.current().getType() == Token.Type.info) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseFinal();
        } else {
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * X → *A
     *
     * @return type: Exp.
     */
    public boolean parseX() {
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
     * Y → aB
     *
     * @return type: Exp.
     */
    public boolean parseY() {
        if (tokenizer.current().getType() == Token.Type.year) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseFinal();
        } else {
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * P → bG
     *
     * @return type: Exp.
     */
    public boolean parseP() {
        if (tokenizer.current().getType() == Token.Type.info) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseFinal();
        } else {
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * Q → aZ
     *
     * @return type: Exp.
     */
    public boolean parseQ() {
        if (tokenizer.current().getType() == Token.Type.year) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            //return parseFinal();
            return parseFinal();
        } else {
            //throw new IllegalProductionException("err for parse P");
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * <R> ::= null | d
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

    public ArrayList<Token> getRes() {
        return parsedList;
    }
}
