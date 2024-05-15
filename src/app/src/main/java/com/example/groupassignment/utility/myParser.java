package com.example.groupassignment.utility;
import java.util.ArrayList;
/**
 * @author jiayu jian u7731262
 */

public interface myParser {
    void handleErr();
    boolean parseExp();
    ArrayList<Token> getRes();
}
