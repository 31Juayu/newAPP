package com.example.groupassignment.utility;


import java.util.ArrayList;
import java.util.List;

//author of this class : jiayu jian
//reference: lab 6
public class smallParserToSearch {
    public static ArrayList<String> findRes(String input, RedBlackTree<String> courseTree) {

        ArrayList<String> ress = new ArrayList<>();

        boolean ifOk = false;
        ArrayList<smallToken> listToken = new ArrayList<>();
        try {
            smallTokenizer tokenizer = new smallTokenizer(input);
            //Parser parser = new Parser(tokenizer);
            smallParser parser = new smallParser(tokenizer);
            ifOk = parser.parseExp();
            listToken = parser.getRes();
        }catch (Exception e){
            ifOk = false;
        }

        if(ifOk){
            for (smallToken e:
                    listToken) {
                String res = courseTree.search(e.getToken());

                if(res != null){
                    ress.add(e.getToken());
                }
            }
        }else{
            System.out.println("not effective");
        }
        return ress;
    }

    public static ArrayList<String> findRessInSmall(String input, List<String> courseList) {
        ArrayList<String> resLine = new ArrayList<>();
        input = parserToSearch.removeExtraCommas(input);
        input = parserToSearch.removeLeadingTrailingCommasAndSpaces(input);
        smallTokenizer tokenizer = new smallTokenizer(input);

        ArrayList<smallToken> listOfToken = new ArrayList<>();
        while (tokenizer.hasNext()){
            listOfToken.add(tokenizer.current());
            tokenizer.next();
        }

        //System.out.println(originalInput);
        ArrayList<String> list = handle_err_Input_Small(listOfToken,courseList);
        for (int i = 0; i < list.size(); i++) {
            String element = list.get(i);

            if (element.endsWith(",")) {
                list.set(i, element.substring(0, element.length() - 1));
            }
        }
        RedBlackTree<String> courseTree = new RedBlackTree<>();
        for (int i = 0; i < courseList.size(); i++) {
            String index = i + "";
            courseTree.put(courseList.get(i).toLowerCase(),index);
        }

        for (String e:
                list) {
            //System.out.println(e);
            ArrayList<String> res = findRes(e,courseTree);
            for (String str:
                    res) {
                resLine.add(str);
            }
        }
        return resLine;
    }

    public static ArrayList<String> handle_err_Input_Small(ArrayList<smallToken> listOfToken,List<String> courseList){
        ArrayList<String> list = new ArrayList<>();

        RedBlackTree<String> courseTree = new RedBlackTree<>();
        for (int i = 0; i < courseList.size(); i++) {
            String index = i + "";
            courseTree.put(courseList.get(i).toLowerCase(),index);
        }

        for (smallToken e:
                listOfToken) {
            if(e.getType()== smallToken.Type.course){
                String element = e.getToken();
                element = element.toLowerCase();
                String value = courseTree.search(element);

                if(value == null){
                    ArrayList<String> similarStrings = findMostSimilarStringInSmallSearch(element,courseList);
                    for (String s:
                            similarStrings) {
                        list.add(s);
                    }
                }else{
                    list.add(element);
                }
            }else{
            }
        }
        return  list;
    }

    public static ArrayList<String> findMostSimilarStringInSmallSearch(String a, List<String> courseList) {
        //String mostSimilarString = null;
        double maxSimilarity = 0.0;
        ArrayList<String> res = new ArrayList<>();

        for (String str : courseList) {
            double similarity = parserToSearch.calculateSimilarity(a, str);
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
}

