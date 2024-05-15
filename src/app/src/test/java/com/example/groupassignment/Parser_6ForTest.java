package com.example.groupassignment;

import com.example.groupassignment.utility.Token;

import java.util.ArrayList;
/**
 * @author Ruize Luo u7776709
 *
 */
public class Parser_6ForTest implements myParserForTest {

    TokenizerForTest tokenizer;
    ArrayList<Token> parsedList;

    public Parser_6ForTest(TokenizerForTest tokenizer) {
        this.tokenizer = tokenizer;
        parsedList = new ArrayList<>();
    }

    /**
     * Adheres to the grammar rule:
     * <S>    ::= d<R> | <R>
     *
     * @return type: Exp.
     */

    // returned is the result of parse
    // default returns the right side
    // since no computation is done, all parse methods are boolean to see if each step holds
    public boolean parseExp() {
        if (tokenizer.current().getType() == Token.Type.quality) {
            parsedList.add(tokenizer.current());
            tokenizer.next(); // consume 'd' terminal
            return parseR(); // parse <R> part
        } else {
            return parseR(); // parse <R> part
        }
    }

    /**
     * Adheres to the grammar rule:
     * R → *M | bN | cH
     *
     * @return type: Exp.
     */
    public boolean parseR() {
        if (tokenizer.current().getType() == Token.Type.asterisk) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseM();
        } else if (tokenizer.current().getType() == Token.Type.info) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseN();
        } else if (tokenizer.current().getType() == Token.Type.country) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseH();
        } else {
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * M → bL | cW
     *
     * @return type: Exp.
     */
    public boolean parseM() {
        if (tokenizer.current().getType() == Token.Type.info) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseL();
        } else if (tokenizer.current().getType() == Token.Type.country) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseW();
        } else {
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * N → *X | cY
     *
     * @return type: Exp.
     */
    public boolean parseN() {
        if (tokenizer.current().getType() == Token.Type.asterisk) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseX();
        } else if (tokenizer.current().getType() == Token.Type.country) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseY();
        } else {
            return false;
        }
    }

    /**
     * Adheres to the grammar rule:
     * H → *P | bQ
     *
     * @return type: Exp.
     */
    public boolean parseH() {
        if (tokenizer.current().getType() == Token.Type.asterisk) {
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
     * L → cE
     *
     * @return type: Exp.
     */
    public boolean parseL() {
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
     * X → cA
     *
     * @return type: Exp.
     */
    public boolean parseX() {
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
     * Y → *B
     *
     * @return type: Exp.
     */
    public boolean parseY() {
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
     * Q → *Z
     *
     * @return type: Exp.
     */
    public boolean parseQ() {
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
     * <R>   ::=  null | d
     *
     * @return type: Exp.
     */
    public boolean parseFinal() {
        if (!tokenizer.hasNext()) {
            return true;
        } else if (tokenizer.current().getType() == Token.Type.quality) {
            parsedList.add(tokenizer.current());
            tokenizer.next(); // consume 'd' terminal
            return !tokenizer.hasNext();
        } else {
            return false;
        }
    }

    public ArrayList<Token> getRes() {
        return parsedList;
    }

}
