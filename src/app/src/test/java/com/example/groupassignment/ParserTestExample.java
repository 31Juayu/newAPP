package com.example.groupassignment;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author jiayu jian u7731262
 * @author Ruize Luo u7776709
 */

public class ParserTestExample {

    @Test
    public void testParser() {
        assertTrue(testCorrectFormat("China, 2015, the number of males employed in non-cultural industries"));
        assertFalse(testCorrectFormat("China, 2015,*, the number of males employed in non-cultural industries"));
        assertFalse(testCorrectFormat("China, 2015,2016, the number of males employed in non-cultural industries,the number of males employed in cultural industries"));
    }

    private boolean testCorrectFormat(String input) {
        TokenizerForTest tokenizer = new TokenizerForTest(input);
        myParserForTest parser = parserFactoryForTest.createParser("Parser", tokenizer);
        return parser.parseExp();
    }

    @Test
    public void testParser_1() {
        TokenizerForTest tokenizer = new TokenizerForTest(generateRandomCombination());
        myParserForTest parser = parserFactoryForTest.createParser("Parser_1", tokenizer);
        boolean ifOk = parser.parseExp();
        assertTrue(ifOk);
    }

    public String generateRandomCombination() {
        Random random = new Random();
        String year = String.valueOf(2015 + random.nextInt(10));
        String quality = random.nextBoolean() ? "quality" : "*";
        String[] parts;
        int choice = random.nextInt(8);
        switch(choice) {
            case 0:
                parts = new String[] {year, "*"};
                break;
            case 1:
                parts = new String[] {"*", year};
                break;
            case 2:
                parts = new String[] {"quality", year, "*"};
                break;
            case 3:
                parts = new String[] {"quality", "*", year};
                break;
            case 4:
                parts = new String[] {year, "*", "quality"};
                break;
            case 5:
                parts = new String[] {"*", year, "quality"};
                break;
            case 6:
                parts = new String[] {"quality", year, "*", "quality"};
                break;
            case 7:
                parts = new String[] {"quality", "*", year, "quality"};
                break;
            default:
                parts = new String[] {};
        }
        return String.join(", ", parts);
    }

    @Test
    public void testParser_2() {
        ArrayList<String> combinations = new ArrayList<>();
        combinations.add("china, *");
        combinations.add("*, china");
        combinations.add("quality, china, *");
        combinations.add("quality, *, china");
        combinations.add("china, *, quality");
        combinations.add("*, china, quality");
        combinations.add("quality, china, *, quality");
        combinations.add("quality, *, china, quality");

        for (String combination : combinations) {
            TokenizerForTest tokenizer = new TokenizerForTest(combination);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_2", tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
            TokenizerForTest tokenizer2 = new TokenizerForTest(generateRandomCombination());
            myParserForTest parser2 = parserFactoryForTest.createParser("Parser_2", tokenizer2);
            boolean ifOk2 = parser2.parseExp();
            assertFalse(ifOk2);
        }
    }

    @Test
    public void testParser_3_withRandom() {
        int numTests = 10;
        for (int i = 0; i < numTests; i++) {
            String randomCombination = generateRandomCombination_1();
            TokenizerForTest tokenizer = new TokenizerForTest(randomCombination);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_3", tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
            String randomCombination2 = generateRandomCombination();
            TokenizerForTest tokenizer2 = new TokenizerForTest(randomCombination2);
            myParserForTest parser2 = parserFactoryForTest.createParser("Parser_3", tokenizer2);
            boolean ifOk2 = parser2.parseExp();
            assertFalse(ifOk2);
        }
    }

    private String generateRandomCombination_1() {
        Random random = new Random();
        String occupation = "Number of persons employed in cultural occupations and working in cultural industries";
        String quality = random.nextBoolean() ? "quality" : "*";
        String[] parts;
        int choice = random.nextInt(8);
        switch(choice) {
            case 0:
                parts = new String[] {occupation, "*"};
                break;
            case 1:
                parts = new String[] {"*", occupation};
                break;
            case 2:
                parts = new String[] {"quality", occupation, "*"};
                break;
            case 3:
                parts = new String[] {"quality", "*", occupation};
                break;
            case 4:
                parts = new String[] {occupation, "*", "quality"};
                break;
            case 5:
                parts = new String[] {"*", occupation, "quality"};
                break;
            case 6:
                parts = new String[] {"quality", occupation, "*", "quality"};
                break;
            case 7:
                parts = new String[] {"quality", "*", occupation, "quality"};
                break;
            default:
                parts = new String[] {};
        }
        return String.join(", ", parts);
    }

    @Test
    public void testParser_4() {
        int numTests = 10;
        for (int i = 0; i < numTests; i++) {
            String randomCombination = generateRandomCombination_4();
            TokenizerForTest tokenizer = new TokenizerForTest(randomCombination);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_4", tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }

    private String generateRandomCombination_4() {
        Random random = new Random();
        String occupation = "Number of persons employed in cultural occupations and working in cultural industries";
        String year = "2015";
        String[] parts;
        int choice = random.nextInt(8);
        switch(choice) {
            case 0:
                parts = new String[] {occupation,year, "*"};
                break;
            case 1:
                parts = new String[] {year,"*", occupation};
                break;
            case 2:
                parts = new String[] {"quality", occupation, year,"*"};
                break;
            case 3:
                parts = new String[] {"quality", year, "*", occupation};
                break;
            case 4:
                parts = new String[] {year,occupation, "*", "quality"};
                break;
            case 5:
                parts = new String[] {"*", year,occupation, "quality"};
                break;
            case 6:
                parts = new String[] {"quality", year,occupation, "*", "quality"};
                break;
            case 7:
                parts = new String[] {"quality", "*", year,occupation, "quality"};
                break;
            default:
                parts = new String[] {};
        }
        return String.join(", ", parts);
    }

    @Test
    public void testParser_5() {
        int numTests = 10;
        for (int i = 0; i < numTests; i++) {
            String randomCombination = generateRandomCombination_5();
            TokenizerForTest tokenizer = new TokenizerForTest(randomCombination);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_5", tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }

    private String generateRandomCombination_5() {
        Random random = new Random();
        String occupation = "China";
        String year = "2015";
        String[] parts;
        int choice = random.nextInt(8);
        switch(choice) {
            case 0:
                parts = new String[] {occupation,year, "*"};
                break;
            case 1:
                parts = new String[] {year,"*", occupation};
                break;
            case 2:
                parts = new String[] {"quality", occupation, year,"*"};
                break;
            case 3:
                parts = new String[] {"quality", year, "*", occupation};
                break;
            case 4:
                parts = new String[] {year,occupation, "*", "quality"};
                break;
            case 5:
                parts = new String[] {"*", year,occupation, "quality"};
                break;
            case 6:
                parts = new String[] {"quality", year,occupation, "*", "quality"};
                break;
            case 7:
                parts = new String[] {"quality", "*", year,occupation, "quality"};
                break;
            default:
                parts = new String[] {};
        }
        return String.join(", ", parts);
    }

    @Test
    public void testParser_6() {
        int numTests = 10;
        for (int i = 0; i < numTests; i++) {
            String randomCombination = generateRandomCombination_6();
            TokenizerForTest tokenizer = new TokenizerForTest(randomCombination);
            myParserForTest parser = parserFactoryForTest.createParser("Parser_6", tokenizer);
            boolean ifOk = parser.parseExp();
            assertTrue(ifOk);
        }
    }

    private String generateRandomCombination_6() {
        Random random = new Random();
        String occupation = "Number of persons employed in cultural occupations and working in cultural industries";
        String year = "China";
        String[] parts;
        int choice = random.nextInt(8);
        switch(choice) {
            case 0:
                parts = new String[] {occupation,year, "*"};
                break;
            case 1:
                parts = new String[] {year,"*", occupation};
                break;
            case 2:
                parts = new String[] {"quality", occupation, year,"*"};
                break;
            case 3:
                parts = new String[] {"quality", year, "*", occupation};
                break;
            case 4:
                parts = new String[] {year,occupation, "*", "quality"};
                break;
            case 5:
                parts = new String[] {"*", year,occupation, "quality"};
                break;
            case 6:
                parts = new String[] {"quality", year,occupation, "*", "quality"};
                break;
            case 7:
                parts = new String[] {"quality", "*", year,occupation, "quality"};
                break;
            default:
                parts = new String[] {};
        }
        return String.join(", ", parts);
    }
}
