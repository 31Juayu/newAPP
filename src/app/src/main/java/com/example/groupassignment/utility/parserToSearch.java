package com.example.groupassignment.utility;
import android.content.Context;

import com.example.groupassignment.utility.myParser;
import com.example.groupassignment.utility.parserFactory;

import java.util.ArrayList;

public class parserToSearch {
    public static ArrayList<ArrayList<String>> findRes(String input, Context context) {
        //res是：数字，（quality）
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
            //得到符合这个年份的所有结果
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
            //得到符合这个国家的所有结果
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
            //得到符合这个信息的所有结果
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
            //得到符合这个年份和信息的所有结果
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
            //得到符合这个年份和国家的所有结果
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
            //得到符合这个信息和国家的所有结果
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
}
