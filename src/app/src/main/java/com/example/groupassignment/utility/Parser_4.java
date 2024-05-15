package com.example.groupassignment.utility;
import android.content.Context;

import com.example.groupassignment.utility.myParser;

import java.util.ArrayList;

//author of this class : jiayu jian
//reference: lab 6
//element: 'a' is year, 'b' is info, 'c' is country, 'd' is 'quality' item, '*'
// is a wildcard character, which represents that other elements can be anything,
// as long as they exist in the CSV table
public class Parser_4 implements myParser {

    Tokenizer tokenizer;
    ArrayList<Token> parsedList;

    Context context;

    public Parser_4(Tokenizer tokenizer,Context context) {
        this.tokenizer = tokenizer;
        parsedList = new ArrayList<>();
        this.context = context;
        handleErr();
    }

    public void handleErr(){
        //use red black tree to remove duplicate type of elements
        RedBlackTree<String> storeTree = new RedBlackTree<>();
        //Tokenizer copiedTokenizer = tokenizer.deepCopy();
        while (tokenizer.hasNext()) {
            storeTree.put(tokenizer.current().getType().name(),tokenizer.current().getToken());
            tokenizer.next();
        }
        //show
        //ArrayList<ArrayList<String>> list  = storeTree.preOrder();
        //case 5:Only 'year' is present, but there are no other elements except '*'
        if(storeTree.search("year") != null &&
                storeTree.search("info") != null &&
                storeTree.search("country") == null &&
                storeTree.search("asterisk") == null){
            storeTree.put("asterisk","*");
        }
        String modifiedInput = "";
        ArrayList<ArrayList<String>> list_1  = storeTree.preOrder();
        for (ArrayList<String> e:
                list_1) {
            //Finally, add 'quality' to ensure compliance with CFG
            if(!e.get(0).equals("quality")){
                modifiedInput = modifiedInput + e.get(1) + ",";
            }
        }
        if(storeTree.search("quality") != null){
            modifiedInput = modifiedInput + "quality" + ",";
        }
        modifiedInput = modifiedInput.substring(0, modifiedInput.length() - 1);
        //System.out.println(modifiedInput);
        tokenizer = new Tokenizer(modifiedInput,context);
    }
    /**
     * Adheres to the grammar rule:
     * <S>    ::= d<R> | <R>
     *
     * @return type: Exp.
     */

    public boolean parseExp() {
        if (tokenizer.current().getType() == Token.Type.quality) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            return parseR();
        } else {
            return parseR();
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
            //return parseFinal();
            return parseM();
        } else if (tokenizer.current().getType() == Token.Type.info) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            //return parseFinal();
            return parseN();
        } else if (tokenizer.current().getType() == Token.Type.asterisk) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            //return parseFinal();
            return parseH();
        } else {
            //return parseFinal();
            //return parseFinal();
            //throw new IllegalProductionException("err for parse R");
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
            //return parseFinal();
            return parseL();
        } else if (tokenizer.current().getType() == Token.Type.asterisk) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            //return parseFinal();
            return parseW();
        } else {
            //throw new IllegalProductionException("err for parse M");
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
            //return parseFinal();
            return parseX();
        } else if (tokenizer.current().getType() == Token.Type.asterisk) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            //return parseFinal();
            return parseY();
        } else {
            //throw new IllegalProductionException("err for parse N");
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
            //return parseFinal();
            return parseP();
        } else if (tokenizer.current().getType() == Token.Type.info) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            //return parseFinal();
            return parseQ();
        } else {
            //throw new IllegalProductionException("err for parse H");
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
            //return parseFinal();
            return parseFinal();
        } else {
            //throw new IllegalProductionException("err for parse L");
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
            //return parseFinal();
            return parseFinal();
        } else {
            //throw new IllegalProductionException("err for parse W");
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
            //return parseFinal();
            return parseFinal();
        } else {
            //throw new IllegalProductionException("err for parse X");
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
            //return parseFinal();
            return parseFinal();
        } else {
            //throw new IllegalProductionException("err for parse Y");
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
            //return parseFinal();
            return parseFinal();
        } else {
            //throw new IllegalProductionException("err for parse P");
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
     * <R>   ::=  null | d
     *
     * @return type: Exp.
     */
    public boolean parseFinal() {
        if(!tokenizer.hasNext()) {
            return true;
        } else if (tokenizer.current().getType() == Token.Type.quality) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            if(!tokenizer.hasNext()){
                return true;
            }else{

                return false;
            }
        } else {
            //System.out.println("false for has next");
            return false;
        }
    }

    public ArrayList<Token> getRes(){
        return parsedList;
    }

}


