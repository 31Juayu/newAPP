package com.example.groupassignment.utility;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

//author of this class : jiayu jian


public class parserToSearch {
    public static ArrayList<ArrayList<String>> findRes(String input, Context context) {
        ArrayList<ArrayList<String>> ress = new ArrayList<>();


        boolean ifOk = false;
        ArrayList<Token> listToken = new ArrayList<>();
        try {
            Tokenizer tokenizer = new Tokenizer(input,context);
            //Parser parser = new Parser(tokenizer);
            myParser parser = parserFactory.createParser("Parser",tokenizer,context);
            ifOk = parser.parseExp();
            listToken = parser.getRes();
        }catch (Exception e){
            ifOk = false;
        }

        boolean ifOk_1 = false;
        ArrayList<Token> listToken_1 = new ArrayList<>();
        try {
            Tokenizer tokenizer = new Tokenizer(input,context);
            //Parser_1 parser_1 = new Parser_1(tokenizer);
            myParser parser_1 = parserFactory.createParser("Parser_1",tokenizer,context);
            ifOk_1 = parser_1.parseExp();
            listToken_1 = parser_1.getRes();
        }catch (Exception e){
            ifOk_1 = false;
        }

        boolean ifOk_2 = false;
        ArrayList<Token> listToken_2 = new ArrayList<>();
        try {
            Tokenizer tokenizer = new Tokenizer(input,context);
            //Parser_2 parser_2 =  new Parser_2(tokenizer);
            myParser parser_2 = parserFactory.createParser("Parser_2",tokenizer,context);
            ifOk_2 = parser_2.parseExp();
            listToken_2 = parser_2.getRes();
        }catch (Exception e){
            ifOk_2 = false;
        }

        boolean ifOk_3 = false;
        ArrayList<Token> listToken_3 = new ArrayList<>();
        try {
            Tokenizer tokenizer = new Tokenizer(input,context);
            //Parser_3 parser_3 =  new Parser_3(tokenizer);
            myParser parser_3 = parserFactory.createParser("Parser_3",tokenizer,context);
            ifOk_3 = parser_3.parseExp();
            listToken_3 = parser_3.getRes();
        } catch (Exception e){
            ifOk_3 = false;
        }

        boolean ifOk_4 = false;
        ArrayList<Token> listToken_4 = new ArrayList<>();
        try {
            Tokenizer tokenizer = new Tokenizer(input,context);
            //Parser_4 parser_4 =  new Parser_4(tokenizer);
            myParser parser_4 = parserFactory.createParser("Parser_4",tokenizer,context);
            ifOk_4 = parser_4.parseExp();
            listToken_4 = parser_4.getRes();
        } catch (Exception e){
            ifOk_4 = false;
        }

        boolean ifOk_5 = false;
        ArrayList<Token> listToken_5 = new ArrayList<>();
        try {
            Tokenizer tokenizer = new Tokenizer(input,context);
            //Parser_5 parser_5 =  new Parser_5(tokenizer);
            myParser parser_5 = parserFactory.createParser("Parser_5",tokenizer,context);
            ifOk_5 = parser_5.parseExp();
            listToken_5 = parser_5.getRes();
        } catch (Exception e){
            ifOk_5 = false;
        }

        boolean ifOk_6 = false;
        ArrayList<Token> listToken_6 = new ArrayList<>();
        try {
            Tokenizer tokenizer = new Tokenizer(input,context);
            //Parser_6 parser_6 =  new Parser_6(tokenizer);
            myParser parser_6 = parserFactory.createParser("Parser_6",tokenizer,context);
            ifOk_6 = parser_6.parseExp();
            listToken_6 = parser_6.getRes();
        } catch (Exception e){
            ifOk_6 = false;
        }

        String year = null;
        String info = null;
        String country = null;
        String quality = null;
        String asterisk = null;

        if(ifOk){//说明这个符合语法
            RedBlackTree<String> treeInfo = usefulMethod.readCSVLinesAllInfo(context);
            RedBlackTree<String> treeCountry = usefulMethod.readCSVLinesAllCountry(context);
            RedBlackTree<String> table = usefulMethod.readCSVLinesAllSearchTable(context);

            ArrayList<String> res = new ArrayList<>();
            for (Token e:
                    listToken) {
                //System.out.print(e.toString() + " ");
                if(e.getType() == Token.Type.year){
                    year = e.getToken();
                } else if (e.getType() == Token.Type.info) {
                    String inputInfo = e.getToken().toLowerCase();
                    info = treeInfo.search(inputInfo);
                } else if (e.getType() == Token.Type.country) {
                    String inputCountry = e.getToken().toLowerCase();
                    country = treeCountry.search(inputCountry);
                } else if (e.getType() == Token.Type.quality) {
                    String inputQuality = e.getToken();
                    quality = inputQuality;
                }else{
                    throw new RuntimeException("Type is err");
                }
            }
            String searchKeyInTable = info + country + year;
            String number = table.search(searchKeyInTable);
            res.add("number is: "+number);
            if(quality != null){
                RedBlackTree<String> qualityTable = usefulMethod.readCSVLinesAllSearchQuality(context);
                String qualityInfo = qualityTable.search(searchKeyInTable);
                res.add(qualityInfo);
            }
            //只有一条信息
            ress.add(res);
        } else if (ifOk_1) {
            for (Token e:
                    listToken_1) {
                //System.out.print(e.toString() + " ");
                if(e.getType() == Token.Type.year){
                    year = e.getToken();
                } else if (e.getType() == Token.Type.asterisk) {
                    asterisk = e.getToken();
                } else if (e.getType() == Token.Type.quality) {
                    quality = e.getToken();
                } else{
                    throw new RuntimeException("Type is err");
                }
            }
            RedBlackTree<String> numRes = usefulMethod.readCSVLinesAllSearchByYear(year,context);
            if(numRes != null){
                //decoder
                ArrayList<ArrayList<String>> list  = numRes.preOrder();
                RedBlackTree<String> qualityTable = usefulMethod.readCSVLinesAllSearchQuality(context);
                for (ArrayList<String> e:
                        list) {
                    //add info
                    ArrayList<String> infoList = usefulMethod.keyDecoder(e.get(0),context);
                    //add res
                    infoList.add("number is: " + e.get(1));
                    if(quality != null){
                        infoList.add(qualityTable.search(e.get(0)));
                        //ualityTable.search(e.get(0));
                    }
                    ress.add(infoList);
                }
            }
        } else if (ifOk_2) {
            for (Token e:
                    listToken_2) {
                //System.out.print(e.toString() + " ");
                if(e.getType() == Token.Type.country){
                    country = e.getToken();
                } else if (e.getType() == Token.Type.asterisk) {
                    asterisk = e.getToken();
                } else if (e.getType() == Token.Type.quality) {
                    quality = e.getToken();
                } else{
                    throw new RuntimeException("Type is err");
                }
            }
            RedBlackTree<String> numRes = usefulMethod.readCSVLinesAllSearchByCountry(country,context);
            if(numRes != null){
                //decoder
                ArrayList<ArrayList<String>> list  = numRes.preOrder();
                RedBlackTree<String> qualityTable = usefulMethod.readCSVLinesAllSearchQuality(context);
                for (ArrayList<String> e:
                        list) {
                    //add info
                    ArrayList<String> infoList = usefulMethod.keyDecoder(e.get(0),context);
                    //add res
                    infoList.add("number is: " + e.get(1));
                    if(quality != null){
                        infoList.add(qualityTable.search(e.get(0)));
                        //ualityTable.search(e.get(0));
                    }
                    ress.add(infoList);
                }
            }

        } else if (ifOk_3) {
            for (Token e:
                    listToken_3) {
                //System.out.print(e.toString() + " ");
                if(e.getType() == Token.Type.info){
                    info = e.getToken();
                } else if (e.getType() == Token.Type.asterisk) {
                    asterisk = e.getToken();
                } else if (e.getType() == Token.Type.quality) {
                    quality = e.getToken();
                } else{
                    throw new RuntimeException("Type is err");
                }
            }
            RedBlackTree<String> numRes = usefulMethod.readCSVLinesAllSearchByInfo(info,context);
            if(numRes != null){
                //decoder
                ArrayList<ArrayList<String>> list  = numRes.preOrder();
                RedBlackTree<String> qualityTable = usefulMethod.readCSVLinesAllSearchQuality(context);
                for (ArrayList<String> e:
                        list) {
                    //add info
                    ArrayList<String> infoList = usefulMethod.keyDecoder(e.get(0),context);
                    //add res
                    infoList.add("number is: " + e.get(1));
                    if(quality != null){
                        infoList.add(qualityTable.search(e.get(0)));
                        //ualityTable.search(e.get(0));
                    }
                    ress.add(infoList);
                }
            }

        } else if (ifOk_4) {
            for (Token e:
                    listToken_4) {
                //System.out.print(e.toString() + " ");
                if(e.getType() == Token.Type.year){
                    year = e.getToken();
                } else if (e.getType() == Token.Type.info) {
                    info = e.getToken();
                } else if (e.getType() == Token.Type.asterisk) {
                    asterisk= e.getToken();
                } else if (e.getType() == Token.Type.quality) {
                    quality = e.getToken();

                }else{
                    throw new RuntimeException("Type is err");
                }
            }

            RedBlackTree<String> numRes = usefulMethod.readCSVLinesAllSearchByYearAndInfo(year,info,context);
            if(numRes != null){
                //decoder
                ArrayList<ArrayList<String>> list  = numRes.preOrder();
                RedBlackTree<String> qualityTable = usefulMethod.readCSVLinesAllSearchQuality(context);
                for (ArrayList<String> e:
                        list) {
                    //add info
                    ArrayList<String> infoList = usefulMethod.keyDecoder(e.get(0),context);
                    //add res
                    infoList.add("number is: " + e.get(1));
                    if(quality != null){
                        infoList.add(qualityTable.search(e.get(0)));
                        //ualityTable.search(e.get(0));
                    }
                    ress.add(infoList);
                }
            }

        } else if (ifOk_5) {
            for (Token e:
                    listToken_5) {
                //System.out.print(e.toString() + " ");
                if(e.getType() == Token.Type.year){
                    year = e.getToken();
                } else if (e.getType() == Token.Type.country) {
                    country = e.getToken();
                } else if (e.getType() == Token.Type.asterisk) {
                    asterisk= e.getToken();
                } else if (e.getType() == Token.Type.quality) {
                    quality = e.getToken();

                }else{
                    throw new RuntimeException("Type is err");
                }
            }

            RedBlackTree<String> numRes = usefulMethod.readCSVLinesAllSearchByYearAndCountry(year,country,context);
            if(numRes != null){
                //decoder
                ArrayList<ArrayList<String>> list  = numRes.preOrder();
                RedBlackTree<String> qualityTable = usefulMethod.readCSVLinesAllSearchQuality(context);
                for (ArrayList<String> e:
                        list) {
                    //add info
                    ArrayList<String> infoList = usefulMethod.keyDecoder(e.get(0),context);
                    //add res
                    infoList.add("number is: " + e.get(1));
                    if(quality != null){
                        infoList.add(qualityTable.search(e.get(0)));
                        //ualityTable.search(e.get(0));
                    }
                    ress.add(infoList);
                }
            }

        } else if (ifOk_6) {
            for (Token e:
                    listToken_6) {
                //System.out.print(e.toString() + " ");
                if(e.getType() == Token.Type.info){
                    info = e.getToken();
                } else if (e.getType() == Token.Type.country) {
                    country = e.getToken();
                } else if (e.getType() == Token.Type.asterisk) {
                    asterisk= e.getToken();
                } else if (e.getType() == Token.Type.quality) {
                    quality = e.getToken();

                }else{
                    throw new RuntimeException("Type is err");
                }
            }

            RedBlackTree<String> numRes = usefulMethod.readCSVLinesAllSearchByInfoAndCountry(info,country,context);
            if(numRes != null){
                //decoder
                ArrayList<ArrayList<String>> list  = numRes.preOrder();
                RedBlackTree<String> qualityTable = usefulMethod.readCSVLinesAllSearchQuality(context);
                for (ArrayList<String> e:
                        list) {
                    //add info
                    ArrayList<String> infoList = usefulMethod.keyDecoder(e.get(0),context);
                    //add res
                    infoList.add("number is: " + e.get(1));
                    if(quality != null){
                        infoList.add(qualityTable.search(e.get(0)));
                        //ualityTable.search(e.get(0));
                    }
                    ress.add(infoList);
                }
            }
        } else{
            System.out.println("not effective");
        }

/*        System.out.println(year);
        System.out.println(info);
        System.out.println(country);
        System.out.println(quality);*/

        return ress;
    }

    public static ArrayList<String> findRess(String input, Context context) {
        ArrayList<String> resLine = new ArrayList<>();
        input = removeExtraCommas(input);
        input = removeLeadingTrailingCommasAndSpaces(input);
        Tokenizer tokenizer = new Tokenizer(input,context);

        ArrayList<Token> listOfToken = new ArrayList<>();
        while (tokenizer.hasNext()){
            listOfToken.add(tokenizer.current());
            tokenizer.next();
        }

        //System.out.println(originalInput);
        ArrayList<String> list = handle_err_Input(listOfToken,context);
        for (int i = 0; i < list.size(); i++) {
            String element = list.get(i);

            if (element.endsWith(",")) {
                list.set(i, element.substring(0, element.length() - 1));
            }
        }

        for (String e:
                list) {
            //System.out.println(e);
            ArrayList<ArrayList<String>> res = findRes(e,context);
            for (ArrayList<String>  strs:
                    res) {
                String thisLine = "";
                for (String j:
                        strs) {
                    thisLine = thisLine + j + ", ";
                }
                resLine.add(thisLine);
            }
        }

        return resLine;
    }



    public static ArrayList<String> handle_err_Input(ArrayList<Token> listOfToken, Context context){
        ArrayList<String> list = new ArrayList<>();

        ArrayList<String> countryNameList = usefulMethod.getAllCountry(context);
        countryNameList.remove(0);
        ArrayList<String> infoList = usefulMethod.getAllInfo(context);
        infoList.remove(0);
        HashSet<String> yearSet = usefulMethod.getAllYear(context);
        yearSet.remove("YEAR");
        RedBlackTree<String> infoTree = usefulMethod.readCSVLinesAllInfo(context);

        for (Token e:
                listOfToken) {
            if(e.getType()== Token.Type.info){
                String element = e.getToken();
                element = element.toLowerCase();
                String value = infoTree.search(element);

                if(value == null){
                    ArrayList<String> similarStrings = findMostSimilarString(element,yearSet,infoList,countryNameList);
                    if(list.isEmpty()){
                        for (String s:
                                similarStrings) {
                            String addedE = s + ",";
                            list.add(addedE);
                        }
                    }else{
/*                        for (int i = 0; i < list.size(); i++) {
                            for (String s:
                                    similarStrings) {
                                String originalItem = list.get(i);
                                list.set(i, originalItem +  + ",");
                            }
                        }*/
                        ArrayList<String> connect = new ArrayList<>();
                        for (int j = 0; j < similarStrings.size(); j++) {
                            for (int i = 0; i < list.size(); i++) {
                                String originalItem = list.get(i);
                                String added = similarStrings.get(j);
                                String newRow = originalItem + added + ",";
                                connect.add(newRow);
                            }
                        }
                        list.clear();
                        for (String newE:
                                connect) {
                            list.add(newE);
                        }
                    }
                }else{
                    if(list.isEmpty()){
                        String thisElement = element + ",";
                        list.add(thisElement);
                    }else{
                        for (int i = 0; i < list.size(); i++) {
                            String originalItem = list.get(i);
                            list.set(i, originalItem + element + ",");
                        }
                    }
                }
            }else{
                if(list.isEmpty()){
                    String thisElement = e.getToken() + ",";
                    list.add(thisElement);
                }else{
                    for (int i = 0; i < list.size(); i++) {
                        String originalItem = list.get(i);
                        list.set(i, originalItem + e.getToken() + ",");
                    }
                }
            }
        }
        return  list;
    }

    public static ArrayList<String> findMostSimilarString(String a, HashSet<String> yearSet,ArrayList<String> infoList,ArrayList<String> countryNameList) {
        //String mostSimilarString = null;
        double maxSimilarity = 0.0;
        ArrayList<String> res = new ArrayList<>();

        for (String str : yearSet) {
            double similarity = calculateSimilarity(a, str);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                res.clear();
                res.add(str);
            } else if (similarity == maxSimilarity) {
                res.add(str);
            }
        }

        for (String str : infoList) {
            double similarity = calculateSimilarity(a, str);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                res.clear();
                res.add(str);
            } else if (similarity == maxSimilarity) {
                res.add(str);
            }
        }

        for (String str : countryNameList) {
            double similarity = calculateSimilarity(a, str);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                res.clear();
                res.add(str);
            } else if (similarity == maxSimilarity) {
                res.add(str);
            }
        }

        return res;
    }

    public static double calculateSimilarity(String a, String str) {
        int totalChars = a.length();
        char[] charArray = a.toCharArray();
        int sameChar = 0;
        for (char e:
                charArray) {
            int index = str.indexOf(e);
            if (index != -1) {
                sameChar = sameChar + 1;
                str = str.substring(index + 1);
            } else {
                str = str;
            }
        }
        //System.out.println((double) sameChar / totalChars);
        return (double) sameChar / totalChars;
    }

    public static String removeExtraCommas(String input) {
        StringBuilder result = new StringBuilder();
        boolean lastWasComma = false;

        int i = 0;
        while (i < input.length()) {
            char current = input.charAt(i);

            if (current == ',') {
                if (!lastWasComma) {
                    result.append(current);
                    lastWasComma = true;
                }

                while (i + 1 < input.length() && Character.isWhitespace(input.charAt(i + 1))) {
                    i++;
                }

                if (i + 1 < input.length() && input.charAt(i + 1) == ',') {
                    i++;
                    continue;
                }
            } else {
                lastWasComma = false;
                result.append(current);
            }
            i++;
        }

        return result.toString();
    }

    public static String removeLeadingTrailingCommasAndSpaces(String input) {
        // First trim the string to remove leading and trailing spaces
        input = input.trim();

        int start = 0;
        int end = input.length() - 1;

        // Skip leading commas
        while (start <= end && input.charAt(start) == ',') {
            start++;
        }

        // Skip trailing commas
        while (end >= start && input.charAt(end) == ',') {
            end--;
        }

        // Return the substring without leading and trailing commas
        return input.substring(start, end + 1);
    }



}
