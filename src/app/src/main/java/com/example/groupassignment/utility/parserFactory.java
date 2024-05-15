package com.example.groupassignment.utility;

import android.content.Context;

import com.example.groupassignment.utility.Parser;
import com.example.groupassignment.utility.myParser;

//author of this class : jiayu jian


public class parserFactory {
    public static myParser createParser(String type, Tokenizer tokenizer, Context context) {
        switch (type) {
            case "Parser":
                return new Parser(tokenizer,context);
            case "Parser_1":
                return new Parser_1(tokenizer,context);
            case "Parser_2":
                return new Parser_2(tokenizer,context);
            case "Parser_3":
                return new Parser_3(tokenizer,context);
            case "Parser_4":
                return new Parser_4(tokenizer,context);
            case "Parser_5":
                return new Parser_5(tokenizer,context);
            case "Parser_6":
                return new Parser_6(tokenizer,context);
            default:
                throw new IllegalArgumentException("Unsupported animal type: " + type);
        }
    }
}
