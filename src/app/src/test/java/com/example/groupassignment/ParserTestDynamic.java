package com.example.groupassignment;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

@RunWith(Parameterized.class)
public class ParserTestDynamic {

    private String input;
    private boolean expectedOutcome;

    // Constructor that takes test parameters.
    public ParserTestDynamic(String input, boolean expectedOutcome) {
        this.input = input;
        this.expectedOutcome = expectedOutcome;
    }

    // Parameter generator method
    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        List<Object[]> params = new ArrayList<>();
        params.addAll(generateRandomTestCases());
        return params;
    }

    // Method to generate a mix of valid and invalid random test cases
    private static List<Object[]> generateRandomTestCases() {
        List<Object[]> testCases = new ArrayList<>();
        // Add logic to generate random strings and expected outcomes
        // Example:
        testCases.add(new Object[]{"China, 2015, the number of males employed in non-cultural industries", true});
        testCases.add(new Object[]{"China, 2015,*, the number of males employed in non-cultural industries", false});
        // Add more randomized and edge cases
        return testCases;
    }

    @Test
    public void testParser() {
        TokenizerForTest tokenizer = new TokenizerForTest(input);
        myParserForTest parser = parserFactoryForTest.createParser("Parser", tokenizer);
        boolean result = parser.parseExp();
        assertEquals(expectedOutcome, result);
    }
}

