package com.example.groupassignment.utility;


import java.util.ArrayList;

public class smallParser {

    smallTokenizer tokenizer;
    ArrayList<smallToken> parsedList;



    public smallParser(smallTokenizer tokenizer) {
        this.tokenizer = tokenizer;
        parsedList = new ArrayList<>();
    }

    /**
     * Adheres to the grammar rule:
     * <S>    ::= a<R> | <R>
     *
     * @return type: Exp.
     */

    public boolean parseExp() {
        if (tokenizer.current().getType() == smallToken.Type.course) {
            parsedList.add(tokenizer.current());
            tokenizer.next(); // 消耗 'd' 终结符
            return parseR(); // 解析 <R> 部分
        } else {
            return parseR(); // 解析 <R> 部分
        }
    }

    /**
     * Adheres to the grammar rule:
     * R → aR | null
     *
     * @return type: Exp.
     */
    public boolean parseR() {
        if(!tokenizer.hasNext()) {
            return true;
        } else if (tokenizer.current().getType() == smallToken.Type.course) {
            parsedList.add(tokenizer.current());
            tokenizer.next(); // 消耗 'a' 终结符
            return parseR();
        } else {
            //System.out.println("false for has next");
            return false;
        }
    }

    public ArrayList<smallToken> getRes(){
        return parsedList;
    }
}
