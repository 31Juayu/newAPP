package com.example.groupassignment.utility;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

//author of this class : jiayu jian
public class usefulMethod {
    public static RedBlackTree<String> readCSVLinesAllCountry(Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_COUNTRY.csv";
        RedBlackTree<String> tree= new RedBlackTree<String>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_COUNTRY.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }

                tree.put(elements[1].toLowerCase(),elements[0].toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static RedBlackTree<String> readCSVLinesAllInfo(Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_LABEL_23.csv";
        RedBlackTree<String> tree= new RedBlackTree<String>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_LABEL_23.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
                String newInfo = elements[1].substring(1, elements[1].length() - 1);
                tree.put(newInfo.toLowerCase(),elements[0].toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static RedBlackTree<String> readCSVLinesAllSearchTable(Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_DATA_NATIONAL_2600.csv";
        RedBlackTree<String> tree= new RedBlackTree<String>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_DATA_NATIONAL_2600.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
/*                String newInfo = elements[1].substring(1, elements[1].length() - 1);
                tree.put(newInfo.toLowerCase(),elements[0].toLowerCase());*/
                String infoCode = elements[0];
                String countryConde = elements[1].toLowerCase();
                String year = elements[2];
                String searchKey = infoCode + countryConde + year;
                tree.put(searchKey,elements[3]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static RedBlackTree<String> readCSVLinesAllSearchQuality(Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_METADATA_547.csv";
        RedBlackTree<String> tree= new RedBlackTree<String>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_METADATA_547.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
/*                String newInfo = elements[1].substring(1, elements[1].length() - 1);
                tree.put(newInfo.toLowerCase(),elements[0].toLowerCase());*/
                String infoCode = elements[0];
                String countryConde = elements[1].toLowerCase();
                String year = elements[2];
                String searchKey = infoCode + countryConde + year;
                String qualityType = elements[3];
                String co = ": ";
                String qualityInfo = elements[4].substring(1, elements[4].length() - 1);
                String quality = qualityType + co + qualityInfo;
                tree.put(searchKey,quality);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static RedBlackTree<String> readCSVLinesAllSearchByYear(String inputYear,Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_DATA_NATIONAL_2600.csv";
        RedBlackTree<String> tree= new RedBlackTree<String>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_DATA_NATIONAL_2600.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
/*                String newInfo = elements[1].substring(1, elements[1].length() - 1);
                tree.put(newInfo.toLowerCase(),elements[0].toLowerCase());*/
                /*String infoCode = elements[0];
                String countryConde = elements[1].toLowerCase();
                String year = elements[2];
                String searchKey = infoCode + countryConde + year;
                tree.put(searchKey,elements[3]);*/
                if(inputYear.equals(elements[2])){
                    String infoCode = elements[0];
                    String countryConde = elements[1].toLowerCase();
                    String year = elements[2];
                    String searchKey = infoCode + countryConde + year;
                    tree.put(searchKey,elements[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }
    public static ArrayList<String> keyDecoder(String key,Context context){
        ArrayList<String> list = new ArrayList<>();
        String infoCode = key.substring(0, 6);
        String countryCode = key.substring(6, 9);
        String year = key.substring(9);
        RedBlackTree<String> infoTree = readCSVLinesAllInfoByKey(context);
        String info = infoTree.search(infoCode);

        RedBlackTree<String> countryKey = readCSVLinesAllCountryByKey(context);
        String country = countryKey.search(countryCode);
        list.add(info);
        list.add(country);
        list.add(year);
        return list;
    }

    public static RedBlackTree<String> readCSVLinesAllCountryByKey(Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_COUNTRY.csv";
        RedBlackTree<String> tree= new RedBlackTree<String>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_COUNTRY.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }

                tree.put(elements[0].toLowerCase(),elements[1].toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static RedBlackTree<String> readCSVLinesAllInfoByKey(Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_LABEL_23.csv";
        RedBlackTree<String> tree= new RedBlackTree<String>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_LABEL_23.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
                String newInfo = elements[1].substring(1, elements[1].length() - 1);
                tree.put(elements[0].toLowerCase(),newInfo.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static RedBlackTree<String> readCSVLinesAllSearchByCountry(String inputCountry,Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_DATA_NATIONAL_2600.csv";
        RedBlackTree<String> tree= new RedBlackTree<String>();
        RedBlackTree<String> countryTree = readCSVLinesAllCountry(context);
        //转化
        String inputCountryCode = countryTree.search(inputCountry.toLowerCase());
        if(inputCountryCode == null){
            return null;
        }
        inputCountryCode = inputCountryCode.toLowerCase();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_DATA_NATIONAL_2600.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
/*                String newInfo = elements[1].substring(1, elements[1].length() - 1);
                tree.put(newInfo.toLowerCase(),elements[0].toLowerCase());*/
                /*String infoCode = elements[0];
                String countryConde = elements[1].toLowerCase();
                String year = elements[2];
                String searchKey = infoCode + countryConde + year;
                tree.put(searchKey,elements[3]);*/
                if(inputCountryCode.equals(elements[1].toLowerCase())){
                    String infoCode = elements[0];
                    String countryConde = elements[1].toLowerCase();
                    String year = elements[2];
                    String searchKey = infoCode + countryConde + year;
                    tree.put(searchKey,elements[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static RedBlackTree<String> readCSVLinesAllSearchByInfo(String inputInfo,Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_DATA_NATIONAL_2600.csv";
        RedBlackTree<String> tree= new RedBlackTree<String>();
        RedBlackTree<String> infoTree = readCSVLinesAllInfo(context);
        //转化
        String inputInfoCode = infoTree.search(inputInfo.toLowerCase());
        if(inputInfoCode == null){
            return null;
        }
        inputInfoCode = inputInfoCode.toLowerCase();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_DATA_NATIONAL_2600.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
/*                String newInfo = elements[1].substring(1, elements[1].length() - 1);
                tree.put(newInfo.toLowerCase(),elements[0].toLowerCase());*/
                /*String infoCode = elements[0];
                String countryConde = elements[1].toLowerCase();
                String year = elements[2];
                String searchKey = infoCode + countryConde + year;
                tree.put(searchKey,elements[3]);*/
                if(inputInfoCode.equals(elements[0].toLowerCase())){
                    String infoCode = elements[0];
                    String countryConde = elements[1].toLowerCase();
                    String year = elements[2];
                    String searchKey = infoCode + countryConde + year;
                    tree.put(searchKey,elements[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static RedBlackTree<String> readCSVLinesAllSearchByYearAndInfo(String InputYear,String inputInfo,Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_DATA_NATIONAL_2600.csv";
        RedBlackTree<String> tree= new RedBlackTree<String>();

        RedBlackTree<String> infoTree = readCSVLinesAllInfo(context);
        //转化
        String inputInfoCode = infoTree.search(inputInfo.toLowerCase());
        if(inputInfoCode == null){
            return null;
        }
        inputInfoCode= inputInfoCode.toLowerCase();


        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_DATA_NATIONAL_2600.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
/*                String newInfo = elements[1].substring(1, elements[1].length() - 1);
                tree.put(newInfo.toLowerCase(),elements[0].toLowerCase());*/
                /*String infoCode = elements[0];
                String countryConde = elements[1].toLowerCase();
                String year = elements[2];
                String searchKey = infoCode + countryConde + year;
                tree.put(searchKey,elements[3]);*/
                if(inputInfoCode.equals(elements[0].toLowerCase()) && InputYear.equals(elements[2])){
                    String infoCode = elements[0];
                    String countryConde = elements[1].toLowerCase();
                    String year = elements[2];
                    String searchKey = infoCode + countryConde + year;
                    tree.put(searchKey,elements[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static RedBlackTree<String> readCSVLinesAllSearchByYearAndCountry(String InputYear,String inputCountry,Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_DATA_NATIONAL_2600.csv";
        RedBlackTree<String> tree= new RedBlackTree<String>();

        RedBlackTree<String> countryTree = readCSVLinesAllCountry(context);
        //转化
        String inputCountryCode = countryTree.search(inputCountry.toLowerCase());
        if(inputCountryCode == null){
            return null;
        }
        inputCountryCode = inputCountryCode.toLowerCase();


        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_DATA_NATIONAL_2600.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
/*                String newInfo = elements[1].substring(1, elements[1].length() - 1);
                tree.put(newInfo.toLowerCase(),elements[0].toLowerCase());*/
                /*String infoCode = elements[0];
                String countryConde = elements[1].toLowerCase();
                String year = elements[2];
                String searchKey = infoCode + countryConde + year;
                tree.put(searchKey,elements[3]);*/
                if(inputCountryCode.equals(elements[1].toLowerCase()) && InputYear.equals(elements[2])){
                    String infoCode = elements[0];
                    String countryConde = elements[1].toLowerCase();
                    String year = elements[2];
                    String searchKey = infoCode + countryConde + year;
                    tree.put(searchKey,elements[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static RedBlackTree<String> readCSVLinesAllSearchByInfoAndCountry(String inputInfo,String inputCountry,Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_DATA_NATIONAL_2600.csv";
        RedBlackTree<String> tree= new RedBlackTree<String>();

        RedBlackTree<String> countryTree = readCSVLinesAllCountry(context);
        //转化
        String inputCountryCode = countryTree.search(inputCountry.toLowerCase());
        if(inputCountryCode == null){
            return null;
        }
        inputCountryCode = inputCountryCode.toLowerCase();

        RedBlackTree<String> infoTree = readCSVLinesAllInfo(context);
        //转化
        String inputInfoCode = infoTree.search(inputInfo.toLowerCase());
        if(inputInfoCode == null){
            return null;
        }
        inputInfoCode= inputInfoCode.toLowerCase();


        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_DATA_NATIONAL_2600.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
/*                String newInfo = elements[1].substring(1, elements[1].length() - 1);
                tree.put(newInfo.toLowerCase(),elements[0].toLowerCase());*/
                /*String infoCode = elements[0];
                String countryConde = elements[1].toLowerCase();
                String year = elements[2];
                String searchKey = infoCode + countryConde + year;
                tree.put(searchKey,elements[3]);*/
                if(inputCountryCode.equals(elements[1].toLowerCase()) && inputInfoCode.equals(elements[0])){
                    String infoCode = elements[0];
                    String countryConde = elements[1].toLowerCase();
                    String year = elements[2];
                    String searchKey = infoCode + countryConde + year;
                    tree.put(searchKey,elements[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public static ArrayList<String> readCSVLinesAllOfInfo(Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_LABEL_23.csv";
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_LABEL_23.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                // 检查最后一个字段后面是否存在额外的逗号，如果有则移除
                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
                elements[1] = elements[1].substring(1, elements[1].length() - 1);
                list.add(elements[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<String> readCSVLinesAllOfCountry(Context context) {

        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_COUNTRY.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                // 检查最后一个字段后面是否存在额外的逗号，如果有则移除
                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
                list.add(elements[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<String> getAllCountry(Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_COUNTRY.csv";
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_COUNTRY.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }

                //tree.put(elements[1].toLowerCase(),elements[0].toLowerCase());
                list.add(elements[1].toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<String> getAllInfo(Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_LABEL_23.csv";
        ArrayList<String> list= new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_LABEL_23.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
                String newInfo = elements[1].substring(1, elements[1].length() - 1);
                list.add(newInfo.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static HashSet<String> getAllYear(Context context) {
        //List<String[]> data = new ArrayList<>();
        //String filePath = "CLTEARCHIVE_DATA_NATIONAL_2600.csv";
        HashSet<String> yearSet= new HashSet<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open("CLTEARCHIVE_DATA_NATIONAL_2600.csv"), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                if (elements.length > 0 && elements[elements.length - 1].isEmpty()) {
                    String[] trimmedElements = new String[elements.length - 1];
                    System.arraycopy(elements, 0, trimmedElements, 0, elements.length - 1);
                    elements = trimmedElements;
                }
                yearSet.add(elements[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return yearSet;
    }
}
