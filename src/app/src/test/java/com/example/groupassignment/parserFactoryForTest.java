package com.example.groupassignment;
/**
 * @author Ruize Luo u7776709
 *
 */
public class parserFactoryForTest {
    public static myParserForTest createParser(String type,TokenizerForTest tokenizer) {
        switch (type) {
            case "Parser":
                return new ParserForTest(tokenizer);
            case "Parser_1":
                return new Parser_1ForTest(tokenizer);
            case "Parser_2":
                return new Parser_2ForTest(tokenizer);
            case "Parser_3":
                return new Parser_3ForTest(tokenizer);
            case "Parser_4":
                return new Parser_4ForTest(tokenizer);
            case "Parser_5":
                return new Parser_5ForTest(tokenizer);
            case "Parser_6":
                return new Parser_6ForTest(tokenizer);
            default:
                throw new IllegalArgumentException("Unsupported animal type: " + type);
        }
    }
}
