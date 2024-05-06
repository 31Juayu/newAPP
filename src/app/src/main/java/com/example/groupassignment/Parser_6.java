package com.example.groupassignment;
import android.content.Context;

import java.util.ArrayList;
import java.util.Scanner;

public class Parser_6 implements myParser {

/*    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }*/

    Tokenizer tokenizer;
    ArrayList<Token> parsedList;

    Context context;

    public Parser_6(Tokenizer tokenizer,Context context) {
        this.tokenizer = tokenizer;
        parsedList = new ArrayList<>();
        this.context = context;
        handleErr();
    }


/*    public static void main(String[] args) {
        // Create a scanner to get the user's input.
        Scanner scanner = new Scanner(System.in);

        System.out.println("Provide a string to be parsed:");
        while (scanner.hasNext()) {
            String input = scanner.nextLine();

            // Check if 'quit' is provided.
            if (input.equals("q"))
                break;

            // Create an instance of the tokenizer.
            Tokenizer tokenizer = new Tokenizer(input);

            // Print out the expression from the parser.
            Parser_6 parser = new Parser_6(tokenizer);
*//*            boolean ifOk = parser.parseExp();
            System.out.println("Parsing: " + ifOk);*//*
        }
    }*/

    public void handleErr(){
        //用红黑树的排重性
        RedBlackTree<String> storeTree = new RedBlackTree<>();
        //Tokenizer copiedTokenizer = tokenizer.deepCopy();
        while (tokenizer.hasNext()) {
            storeTree.put(tokenizer.current().getType().name(),tokenizer.current().getToken());
            tokenizer.next();
        }
        //show
        //ArrayList<ArrayList<String>> list  = storeTree.preOrder();
        //case 7:只有info和country没有其他的，但是没*
        if(storeTree.search("year") == null &&
                storeTree.search("info") != null &&
                storeTree.search("country") != null &&
                storeTree.search("asterisk") == null){
            storeTree.put("asterisk","*");
        }
        String modifiedInput = "";
        ArrayList<ArrayList<String>> list_1  = storeTree.preOrder();
        for (ArrayList<String> e:
                list_1) {
            //最后再加入quality以保证符合CFG
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

    //returned 就是parse的结果
    //默认返回右边的
    //由于不做任何计算，所以所有的parse方法都是boolean，看每一步是否hold。
    public boolean parseExp() {
/*        Exp res = null; //最终返回的exp结果
        //保存原来的tokenizer
        Tokenizer ori_tokenizer = tokenizer.deepCopy();
        Exp d = parseLeftTerminal_d(); //左边的等式，假设会移动tokenizer
        if(d == null){
            tokenizer = ori_tokenizer;

        }else{

        }
        Exp R; //右边的等式
        if(tokenizer.hasNext()){
            R = parseR();
        }*/
        if (tokenizer.current().getType() == Token.Type.quality) {
            parsedList.add(tokenizer.current());
            tokenizer.next(); // 消耗 'd' 终结符
            return parseR(); // 解析 <R> 部分
        } else {
            return parseR(); // 解析 <R> 部分
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
            //return parseFinal();
            return parseM();
        } else if (tokenizer.current().getType() == Token.Type.info) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            //return parseFinal();
            return parseN();
        } else if (tokenizer.current().getType() == Token.Type.country) {
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
     * M → bL | cW
     *
     * @return type: Exp.
     */
    public boolean parseM() {
        if (tokenizer.current().getType() == Token.Type.info) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            //return parseFinal();
            return parseL();
        } else if (tokenizer.current().getType() == Token.Type.country) {
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
     * N → *X | cY
     *
     * @return type: Exp.
     */
    public boolean parseN() {
        if (tokenizer.current().getType() == Token.Type.asterisk) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            //return parseFinal();
            return parseX();
        } else if (tokenizer.current().getType() == Token.Type.country) {
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
     * H → *P | bQ
     *
     * @return type: Exp.
     */
    public boolean parseH() {
        if (tokenizer.current().getType() == Token.Type.asterisk) {
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
     * L → cE
     *
     * @return type: Exp.
     */
    public boolean parseL() {
        if (tokenizer.current().getType() == Token.Type.country) {
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
     * X → cA
     *
     * @return type: Exp.
     */
    public boolean parseX() {
        if (tokenizer.current().getType() == Token.Type.country) {
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
     * Y → *B
     *
     * @return type: Exp.
     */
    public boolean parseY() {
        if (tokenizer.current().getType() == Token.Type.asterisk) {
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
     * Q → *Z
     *
     * @return type: Exp.
     */
    public boolean parseQ() {
        if (tokenizer.current().getType() == Token.Type.asterisk) {
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
            tokenizer.next(); // 消耗 'd' 终结符
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

/*    public Exp parseLeftTerminal_d(){
        Exp res = null;
        if(tokenizer.hasNext()){
//            Token current = tokenizer.current();
            Token.Type type = tokenizer.current().getType();

            if (type == Token.Type.quality) {
                tokenizer.next();
                res = new qualityExp();
            }
//            else {
//                throw new IllegalProductionException("err for parse d");
//            }
        }

        return res;
    }*/


}


