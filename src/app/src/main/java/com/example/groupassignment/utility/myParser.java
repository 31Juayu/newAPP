package com.example.groupassignment.utility;
import java.util.ArrayList;
//author of this class : jiayu jian
public interface myParser {
    void handleErr();
    boolean parseExp();
    ArrayList<Token> getRes();
}
