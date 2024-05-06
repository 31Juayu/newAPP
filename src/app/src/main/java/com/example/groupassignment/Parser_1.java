package com.example.groupassignment;
import android.content.Context;

import java.util.ArrayList;
import java.util.Scanner;

public class Parser_1 implements myParser {

/*    public static class IllegalProductionException extends IllegalArgumentException {
        public IllegalProductionException(String errorMessage) {
            super(errorMessage);
        }
    }*/

    Tokenizer tokenizer;
    ArrayList<Token> parsedList;

    Context context;

    public Parser_1(Tokenizer tokenizer,Context context) {
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
            Parser_1 parser = new Parser_1(tokenizer);
         *//*   boolean ifOk = parser.parseExp();
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
        //case 2:只有year没有其他的，但是没*
        if(storeTree.search("year") != null &&
                storeTree.search("info") == null &&
                storeTree.search("country") == null &&
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
     * R → aM | *N
     *
     * @return type: Exp.
     */
    public boolean parseR() {
        if (tokenizer.current().getType() == Token.Type.year) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            //return parseFinal();
            return parseM();
        }else if (tokenizer.current().getType() == Token.Type.asterisk) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            //return parseFinal();
            return parseN();
        } else {
            //return parseFinal();
            //return parseFinal();
            //throw new IllegalProductionException("err for parse R");
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
            //return parseFinal();
            return parseFinal();
        } else {
            //throw new IllegalProductionException("err for parse M");
            return false;
        }
    }
    /**
     * Adheres to the grammar rule:
     * N → aF
     *
     * @return type: Exp.
     */
    public boolean parseN() {
        if (tokenizer.current().getType() == Token.Type.year) {
            parsedList.add(tokenizer.current());
            tokenizer.next();
            //return parseFinal();
            return parseFinal();
        } else {
            //throw new IllegalProductionException("err for parse N");
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

}


