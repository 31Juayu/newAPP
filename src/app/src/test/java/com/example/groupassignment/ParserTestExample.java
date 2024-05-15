package com.example.groupassignment;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class ParserTestExample {

/*    @Test
    public void testTokenizer(){
        String info = "China, 2015, the number of males employed in non-cultural industries";
*//*        TokenizerForTest tokenizer = new TokenizerForTest(info);
        while (tokenizer.hasNext()){
            System.out.println(tokenizer.current().getType());
            tokenizer.next();
        }*//*
        TokenizerForTest tokenizer = new TokenizerForTest(info);
        myParserForTest parser = parserFactoryForTest.createParser("Parser",tokenizer);
        boolean ifOk = parser.parseExp();
        System.out.println("the res is " + ifOk);
    }*/

    @Test
    public void testParser() {
        ArrayList<String> combinations = new ArrayList<>();

        // 第一组
        combinations.add("China, 2015, the number of males employed in non-cultural industries");
        combinations.add("China, the number of males employed in non-cultural industries, 2015");
        combinations.add("the number of males employed in non-cultural industries, China, 2015");
        combinations.add("the number of males employed in non-cultural industries, 2015, China");
        combinations.add("2015, China, the number of males employed in non-cultural industries");
        combinations.add("2015, the number of males employed in non-cultural industries, China");

        // 第二组
        combinations.add("quality, China, 2015, the number of males employed in non-cultural industries");
        combinations.add("quality, China, the number of males employed in non-cultural industries, 2015");
        combinations.add("quality, the number of males employed in non-cultural industries, China, 2015");
        combinations.add("quality, the number of males employed in non-cultural industries, 2015, China");
        combinations.add("quality, 2015, China, the number of males employed in non-cultural industries");
        combinations.add("quality, 2015, the number of males employed in non-cultural industries, China");

        // 第三组
        combinations.add("China, the number of males employed in non-cultural industries, 2015, quality");
        combinations.add("China, 2015, the number of males employed in non-cultural industries, quality");
        combinations.add("the number of males employed in non-cultural industries, China, 2015, quality");
        combinations.add("the number of males employed in non-cultural industries, 2015, China, quality");
        combinations.add("2015, China, the number of males employed in non-cultural industries, quality");
        combinations.add("2015, the number of males employed in non-cultural industries, China, quality");

        // 第四组
        combinations.add("quality, China, the number of males employed in non-cultural industries, 2015, quality");
        combinations.add("quality, China, 2015, the number of males employed in non-cultural industries, quality");
        combinations.add("quality, the number of males employed in non-cultural industries, China, 2015, quality");
        combinations.add("quality, the number of males employed in non-cultural industries, 2015, China, quality");
        combinations.add("quality, 2015, China, the number of males employed in non-cultural industries, quality");
        combinations.add("quality, 2015, the number of males employed in non-cultural industries, China, quality");

        // 输出ArrayList中的所有组合
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            //一个parser对应一个输入
            myParserForTest parser = parserFactoryForTest.createParser("Parser",tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
        //写错的那种，不符合以上信息顺序的的都是错的：
        String info = "China, 2015,*, the number of males employed in non-cultural industries";
        TokenizerForTest tokenizer = new TokenizerForTest(info);
        //一个parser对应一个输入
        myParserForTest parser = parserFactoryForTest.createParser("Parser",tokenizer);
        boolean ifOk = parser.parseExp();
        assertFalse(ifOk);

        String info2 = "China, 2015,2016, the number of males employed in non-cultural industries,the number of males employed in cultural industries";
        TokenizerForTest tokenizer2 = new TokenizerForTest(info2);
        //一个parser对应一个输入
        myParserForTest parser2 = parserFactoryForTest.createParser("Parser",tokenizer2);
        boolean ifOk2 = parser2.parseExp();
        assertFalse(ifOk2);
    }

    @Test
    public void testParser_1() {
        ArrayList<String> combinations = new ArrayList<>();

        // 第一组
        combinations.add("2015, *");
        combinations.add("*, 2015");

        // 第二组
        combinations.add("quality, 2015, *");
        combinations.add("quality, *, 2015");

        combinations.add("2015, *, quality");
        combinations.add("*, 2015, quality");

        combinations.add("quality, 2015, *, quality");
        combinations.add("quality, *, 2015, quality");

        // 输出ArrayList中的所有组合
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            //一个parser对应一个输入
            //Parser_1 parser = new Parser_1(tokenizer);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_1",tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }
    @Test
    public void testParser_2() {
        ArrayList<String> combinations = new ArrayList<>();

        // 第一组
        combinations.add("china, *");
        combinations.add("*, china");

        // 第二组
        combinations.add("quality, china, *");
        combinations.add("quality, *, china");

        combinations.add("china, *, quality");
        combinations.add("*, china, quality");

        combinations.add("quality, china, *, quality");
        combinations.add("quality, *, china, quality");

        // 输出ArrayList中的所有组合
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            //一个parser对应一个输入
            //Parser_2 parser = new Parser_2(tokenizer);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_2",tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }
    @Test
    public void testParser_3() {
        ArrayList<String> combinations = new ArrayList<>();

        // 第一组
        combinations.add("Number of persons employed in cultural occupations and working in cultural industries, *");
        combinations.add("*, Number of persons employed in cultural occupations and working in cultural industries");

        // 第二组
        combinations.add("quality, Number of persons employed in cultural occupations and working in cultural industries, *");
        combinations.add("quality, *, Number of persons employed in cultural occupations and working in cultural industries");

        combinations.add("Number of persons employed in cultural occupations and working in cultural industries, *, quality");
        combinations.add("*, Number of persons employed in cultural occupations and working in cultural industries, quality");

        combinations.add("quality, Number of persons employed in cultural occupations and working in cultural industries, *, quality");
        combinations.add("quality, *, Number of persons employed in cultural occupations and working in cultural industries, quality");

        // 输出ArrayList中的所有组合
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            //一个parser对应一个输入
            //Parser_3 parser = new Parser_3(tokenizer);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_3",tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }

    @Test
    public void testParser_4() {
        ArrayList<String> combinations = new ArrayList<>();

        // 第一组
        combinations.add("*, 2015, the number of males employed in non-cultural industries");
        combinations.add("*, the number of males employed in non-cultural industries, 2015");
        combinations.add("the number of males employed in non-cultural industries, *, 2015");
        combinations.add("the number of males employed in non-cultural industries, 2015, *");
        combinations.add("2015, *, the number of males employed in non-cultural industries");
        combinations.add("2015, the number of males employed in non-cultural industries, *");

        // 第二组
        combinations.add("quality, *, 2015, the number of males employed in non-cultural industries");
        combinations.add("quality, *, the number of males employed in non-cultural industries, 2015");
        combinations.add("quality, the number of males employed in non-cultural industries, *, 2015");
        combinations.add("quality, the number of males employed in non-cultural industries, 2015, *");
        combinations.add("quality, 2015, *, the number of males employed in non-cultural industries");
        combinations.add("quality, 2015, the number of males employed in non-cultural industries, *");

        // 第三组
        combinations.add("*, the number of males employed in non-cultural industries, 2015, quality");
        combinations.add("*, 2015, the number of males employed in non-cultural industries, quality");
        combinations.add("the number of males employed in non-cultural industries, *, 2015, quality");
        combinations.add("the number of males employed in non-cultural industries, 2015, *, quality");
        combinations.add("2015, *, the number of males employed in non-cultural industries, quality");
        combinations.add("2015, the number of males employed in non-cultural industries, *, quality");

        // 第四组
        combinations.add("quality, *, the number of males employed in non-cultural industries, 2015, quality");
        combinations.add("quality, *, 2015, the number of males employed in non-cultural industries, quality");
        combinations.add("quality, the number of males employed in non-cultural industries, *, 2015, quality");
        combinations.add("quality, the number of males employed in non-cultural industries, 2015, *, quality");
        combinations.add("quality, 2015, *, the number of males employed in non-cultural industries, quality");
        combinations.add("quality, 2015, the number of males employed in non-cultural industries, *, quality");

        // 输出ArrayList中的所有组合
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            //一个parser对应一个输入
            //Parser_4 parser = new Parser_4(tokenizer);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_4",tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }

    @Test
    public void testParser_5() {
        ArrayList<String> combinations = new ArrayList<>();

        // 第一组
        combinations.add("China, 2015, *");
        combinations.add("China, *, 2015");
        combinations.add("*, China, 2015");
        combinations.add("*, 2015, China");
        combinations.add("2015, China, *");
        combinations.add("2015, *, China");

        // 第二组
        combinations.add("quality, China, 2015, *");
        combinations.add("quality, China, *, 2015");
        combinations.add("quality, *, China, 2015");
        combinations.add("quality, *, 2015, China");
        combinations.add("quality, 2015, China, *");
        combinations.add("quality, 2015, *, China");

        // 第三组
        combinations.add("China, *, 2015, quality");
        combinations.add("China, 2015, *, quality");
        combinations.add("*, China, 2015, quality");
        combinations.add("*, 2015, China, quality");
        combinations.add("2015, China, *, quality");
        combinations.add("2015, *, China, quality");

        // 第四组
        combinations.add("quality, China, *, 2015, quality");
        combinations.add("quality, China, 2015, *, quality");
        combinations.add("quality, *, China, 2015, quality");
        combinations.add("quality, *, 2015, China, quality");
        combinations.add("quality, 2015, China, *, quality");
        combinations.add("quality, 2015, *, China, quality");

        // 输出ArrayList中的所有组合
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            //一个parser对应一个输入
            //Parser_5 parser = new Parser_5(tokenizer);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_5",tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }

    @Test
    public void testParser_6() {
        ArrayList<String> combinations = new ArrayList<>();

        // 第一组
        combinations.add("China, *, the number of males employed in non-cultural industries");
        combinations.add("China, the number of males employed in non-cultural industries, *");
        combinations.add("the number of males employed in non-cultural industries, China, *");
        combinations.add("the number of males employed in non-cultural industries, *, China");
        combinations.add("*, China, the number of males employed in non-cultural industries");
        combinations.add("*, the number of males employed in non-cultural industries, China");

        // 第二组
        combinations.add("quality, China, *, the number of males employed in non-cultural industries");
        combinations.add("quality, China, the number of males employed in non-cultural industries, *");
        combinations.add("quality, the number of males employed in non-cultural industries, China, *");
        combinations.add("quality, the number of males employed in non-cultural industries, *, China");
        combinations.add("quality, *, China, the number of males employed in non-cultural industries");
        combinations.add("quality, *, the number of males employed in non-cultural industries, China");

        // 第三组
        combinations.add("China, the number of males employed in non-cultural industries, *, quality");
        combinations.add("China, *, the number of males employed in non-cultural industries, quality");
        combinations.add("the number of males employed in non-cultural industries, China, *, quality");
        combinations.add("the number of males employed in non-cultural industries, *, China, quality");
        combinations.add("*, China, the number of males employed in non-cultural industries, quality");
        combinations.add("*, the number of males employed in non-cultural industries, China, quality");

        // 第四组
        combinations.add("quality, China, the number of males employed in non-cultural industries, *, quality");
        combinations.add("quality, China, *, the number of males employed in non-cultural industries, quality");
        combinations.add("quality, the number of males employed in non-cultural industries, China, *, quality");
        combinations.add("quality, the number of males employed in non-cultural industries, *, China, quality");
        combinations.add("quality, *, China, the number of males employed in non-cultural industries, quality");
        combinations.add("quality, *, the number of males employed in non-cultural industries, China, quality");

        // 输出ArrayList中的所有组合
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            //一个parser对应一个输入
            //Parser_6 parser = new Parser_6(tokenizer);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_6",tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }

}