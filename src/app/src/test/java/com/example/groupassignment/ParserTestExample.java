package com.example.groupassignment;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
/**
 * @author Ruize Luo u7776709
 *
 */
public class ParserTestExample {
    @Test
    public void testParser() {
        ArrayList<String> combinations = new ArrayList<>();

        // Group 1
        combinations.add("China, 2015, the number of males employed in non-cultural industries");
        combinations.add("China, the number of males employed in non-cultural industries, 2015");
        combinations.add("the number of males employed in non-cultural industries, China, 2015");
        combinations.add("the number of males employed in non-cultural industries, 2015, China");
        combinations.add("2015, China, the number of males employed in non-cultural industries");
        combinations.add("2015, the number of males employed in non-cultural industries, China");

        // Group 2
        combinations.add("quality, China, 2015, the number of males employed in non-cultural industries");
        combinations.add("quality, China, the number of males employed in non-cultural industries, 2015");
        combinations.add("quality, the number of males employed in non-cultural industries, China, 2015");
        combinations.add("quality, the number of males employed in non-cultural industries, 2015, China");
        combinations.add("quality, 2015, China, the number of males employed in non-cultural industries");
        combinations.add("quality, 2015, the number of males employed in non-cultural industries, China");

        // Group 3
        combinations.add("China, the number of males employed in non-cultural industries, 2015, quality");
        combinations.add("China, 2015, the number of males employed in non-cultural industries, quality");
        combinations.add("the number of males employed in non-cultural industries, China, 2015, quality");
        combinations.add("the number of males employed in non-cultural industries, 2015, China, quality");
        combinations.add("2015, China, the number of males employed in non-cultural industries, quality");
        combinations.add("2015, the number of males employed in non-cultural industries, China, quality");

        // Group 4
        combinations.add("quality, China, the number of males employed in non-cultural industries, 2015, quality");
        combinations.add("quality, China, 2015, the number of males employed in non-cultural industries, quality");
        combinations.add("quality, the number of males employed in non-cultural industries, China, 2015, quality");
        combinations.add("quality, the number of males employed in non-cultural industries, 2015, China, quality");
        combinations.add("quality, 2015, China, the number of males employed in non-cultural industries, quality");
        combinations.add("quality, 2015, the number of males employed in non-cultural industries, China, quality");

        // Output all combinations in ArrayList
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            // One parser for one input
            myParserForTest parser = parserFactoryForTest.createParser("Parser", tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }

        // Incorrect combinations that do not follow the above patterns
        String info = "China, 2015,*, the number of males employed in non-cultural industries";
        TokenizerForTest tokenizer = new TokenizerForTest(info);
        myParserForTest parser = parserFactoryForTest.createParser("Parser", tokenizer);
        boolean ifOk = parser.parseExp();
        assertFalse(ifOk);

        String info2 = "China, 2015,2016, the number of males employed in non-cultural industries,the number of males employed in cultural industries";
        TokenizerForTest tokenizer2 = new TokenizerForTest(info2);
        myParserForTest parser2 = parserFactoryForTest.createParser("Parser", tokenizer2);
        boolean ifOk2 = parser2.parseExp();
        assertFalse(ifOk2);
    }

    @Test
    public void testParser_1() {
        ArrayList<String> combinations = new ArrayList<>();

        // Group 1
        combinations.add("2015, *");
        combinations.add("*, 2015");

        // Group 2
        combinations.add("quality, 2015, *");
        combinations.add("quality, *, 2015");
        combinations.add("2015, *, quality");
        combinations.add("*, 2015, quality");
        combinations.add("quality, 2015, *, quality");
        combinations.add("quality, *, 2015, quality");

        // Output all combinations in ArrayList
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_1", tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }

    @Test
    public void testParser_2() {
        ArrayList<String> combinations = new ArrayList<>();

        // Group 1
        combinations.add("china, *");
        combinations.add("*, china");

        // Group 2
        combinations.add("quality, china, *");
        combinations.add("quality, *, china");
        combinations.add("china, *, quality");
        combinations.add("*, china, quality");
        combinations.add("quality, china, *, quality");
        combinations.add("quality, *, china, quality");

        // Output all combinations in ArrayList
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_2", tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }

    @Test
    public void testParser_3() {
        ArrayList<String> combinations = new ArrayList<>();

        // Group 1
        combinations.add("Number of persons employed in cultural occupations and working in cultural industries, *");
        combinations.add("*, Number of persons employed in cultural occupations and working in cultural industries");

        // Group 2
        combinations.add("quality, Number of persons employed in cultural occupations and working in cultural industries, *");
        combinations.add("quality, *, Number of persons employed in cultural occupations and working in cultural industries");
        combinations.add("Number of persons employed in cultural occupations and working in cultural industries, *, quality");
        combinations.add("*, Number of persons employed in cultural occupations and working in cultural industries, quality");
        combinations.add("quality, Number of persons employed in cultural occupations and working in cultural industries, *, quality");
        combinations.add("quality, *, Number of persons employed in cultural occupations and working in cultural industries, quality");

        // Output all combinations in ArrayList
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_3", tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }

    @Test
    public void testParser_4() {
        ArrayList<String> combinations = new ArrayList<>();

        // Group 1
        combinations.add("*, 2015, the number of males employed in non-cultural industries");
        combinations.add("*, the number of males employed in non-cultural industries, 2015");
        combinations.add("the number of males employed in non-cultural industries, *, 2015");
        combinations.add("the number of males employed in non-cultural industries, 2015, *");
        combinations.add("2015, *, the number of males employed in non-cultural industries");
        combinations.add("2015, the number of males employed in non-cultural industries, *");

        // Group 2
        combinations.add("quality, *, 2015, the number of males employed in non-cultural industries");
        combinations.add("quality, *, the number of males employed in non-cultural industries, 2015");
        combinations.add("quality, the number of males employed in non-cultural industries, *, 2015");
        combinations.add("quality, the number of males employed in non-cultural industries, 2015, *");
        combinations.add("quality, 2015, *, the number of males employed in non-cultural industries");
        combinations.add("quality, 2015, the number of males employed in non-cultural industries, *");

        // Group 3
        combinations.add("*, the number of males employed in non-cultural industries, 2015, quality");
        combinations.add("*, 2015, the number of males employed in non-cultural industries, quality");
        combinations.add("the number of males employed in non-cultural industries, *, 2015, quality");
        combinations.add("the number of males employed in non-cultural industries, 2015, *, quality");
        combinations.add("2015, *, the number of males employed in non-cultural industries, quality");
        combinations.add("2015, the number of males employed in non-cultural industries, *, quality");

        // Group 4
        combinations.add("quality, *, the number of males employed in non-cultural industries, 2015, quality");
        combinations.add("quality, *, 2015, the number of males employed in non-cultural industries, quality");
        combinations.add("quality, the number of males employed in non-cultural industries, *, 2015, quality");
        combinations.add("quality, the number of males employed in non-cultural industries, 2015, *, quality");
        combinations.add("quality, 2015, *, the number of males employed in non-cultural industries, quality");
        combinations.add("quality, 2015, the number of males employed in non-cultural industries, *, quality");

        // Output all combinations in ArrayList
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_4", tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }

    @Test
    public void testParser_5() {
        ArrayList<String> combinations = new ArrayList<>();

        // Group 1
        combinations.add("China, 2015, *");
        combinations.add("China, *, 2015");
        combinations.add("*, China, 2015");
        combinations.add("*, 2015, China");
        combinations.add("2015, China, *");
        combinations.add("2015, *, China");

        // Group 2
        combinations.add("quality, China, 2015, *");
        combinations.add("quality, China, *, 2015");
        combinations.add("quality, *, China, 2015");
        combinations.add("quality, *, 2015, China");
        combinations.add("quality, 2015, China, *");
        combinations.add("quality, 2015, *, China");

        // Group 3
        combinations.add("China, *, 2015, quality");
        combinations.add("China, 2015, *, quality");
        combinations.add("*, China, 2015, quality");
        combinations.add("*, 2015, China, quality");
        combinations.add("2015, China, *, quality");
        combinations.add("2015, *, China, quality");

        // Group 4
        combinations.add("quality, China, *, 2015, quality");
        combinations.add("quality, China, 2015, *, quality");
        combinations.add("quality, *, China, 2015, quality");
        combinations.add("quality, *, 2015, China, quality");
        combinations.add("quality, 2015, China, *, quality");
        combinations.add("quality, 2015, *, China, quality");

        // Output all combinations in ArrayList
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_5", tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }

    @Test
    public void testParser_6() {
        ArrayList<String> combinations = new ArrayList<>();

        // Group 1
        combinations.add("China, *, the number of males employed in non-cultural industries");
        combinations.add("China, the number of males employed in non-cultural industries, *");
        combinations.add("the number of males employed in non-cultural industries, China, *");
        combinations.add("the number of males employed in non-cultural industries, *, China");
        combinations.add("*, China, the number of males employed in non-cultural industries");
        combinations.add("*, the number of males employed in non-cultural industries, China");

        // Group 2
        combinations.add("quality, China, *, the number of males employed in non-cultural industries");
        combinations.add("quality, China, the number of males employed in non-cultural industries, *");
        combinations.add("quality, the number of males employed in non-cultural industries, China, *");
        combinations.add("quality, the number of males employed in non-cultural industries, *, China");
        combinations.add("quality, *, China, the number of males employed in non-cultural industries");
        combinations.add("quality, *, the number of males employed in non-cultural industries, China");

        // Group 3
        combinations.add("China, the number of males employed in non-cultural industries, *, quality");
        combinations.add("China, *, the number of males employed in non-cultural industries, quality");
        combinations.add("the number of males employed in non-cultural industries, China, *, quality");
        combinations.add("the number of males employed in non-cultural industries, *, China, quality");
        combinations.add("*, China, the number of males employed in non-cultural industries, quality");
        combinations.add("*, the number of males employed in non-cultural industries, China, quality");

        // Group 4
        combinations.add("quality, China, the number of males employed in non-cultural industries, *, quality");
        combinations.add("quality, China, *, the number of males employed in non-cultural industries, quality");
        combinations.add("quality, the number of males employed in non-cultural industries, China, *, quality");
        combinations.add("quality, the number of males employed in non-cultural industries, *, China, quality");
        combinations.add("quality, *, China, the number of males employed in non-cultural industries, quality");
        combinations.add("quality, *, the number of males employed in non-cultural industries, China, quality");

        // Output all combinations in ArrayList
        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_6", tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }
}
