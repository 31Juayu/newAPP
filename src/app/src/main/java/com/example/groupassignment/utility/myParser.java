package com.example.groupassignment.utility;
import java.util.ArrayList;

public interface myParser {
    void handleErr();
    boolean parseExp();
    ArrayList<Token> getRes();
}
