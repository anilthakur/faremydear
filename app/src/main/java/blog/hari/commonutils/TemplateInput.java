package blog.hari.commonutils;

import static blog.hari.commonutils.CommonConstants.COLUMN_SEARCH_KEYWORD;
import static blog.hari.commonutils.CommonConstants.TEMPLATE_SEARCH_KEYWORD;
import static blog.hari.commonutils.Constants.FILE_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class TemplateInput {
    public static List<String> listOfFinalHeaderItem = new ArrayList<>();
    public static LinkedHashMap<String, String> vendorItem = new LinkedHashMap<>();
    public static String fileName = "";

    public static List<String> getTemplateFixedInputData() {
        String[] listOfTitle = {"serial", "item", "specification", "size 1", "size 2", "grade", "unit", "Quantity (R0)", "Contingencies(10%)", "Total Quantity"};
        ArrayList<String> listOfTemplateItem = new ArrayList<>();
        for (String item : listOfTitle) {
            listOfTemplateItem.add(item);
        }
        return listOfTemplateItem;

    }


    public static String getTemplateVoiceData(String input) {
        String finalData = "";

        try {
            if (containsWords(input, TEMPLATE_SEARCH_KEYWORD) != null && containsWords(input, COLUMN_SEARCH_KEYWORD) != null) {
                fileName = findTextBetween(input, containsWords(input, TEMPLATE_SEARCH_KEYWORD), containsWords(input, COLUMN_SEARCH_KEYWORD));
                DataProcessor.saveString(fileName + ".xls", FILE_NAME);
                finalData = fileName + " |";
            }
            if (containsWords(input, COLUMN_SEARCH_KEYWORD) != null) {
                String column = input.substring(input.indexOf(containsWords(input, COLUMN_SEARCH_KEYWORD)) + containsWords(input, COLUMN_SEARCH_KEYWORD).length()).replaceAll(",", "|");
                geVendorColumnName(column.toLowerCase());
                finalData = finalData + column.toLowerCase();
            }

        } catch (Exception e) {
            finalData = finalData + " |";
        }

        return finalData;

    }

    public static String excelFileName(String input) {
        return input;
    }

    public static List<String> listOfHeaderItem(String input) {
        List<String> finalData = new ArrayList<>();
        finalData.addAll(getTemplateFixedInputData());
        String[] vendor = getItem(input);
        for (int i = 1; i < vendor.length; i++) {
            finalData.add(vendor[i]);
        }
        listOfFinalHeaderItem(finalData);
        return finalData;
    }

    public static void listOfFinalHeaderItem(List<String> stringList) {
        listOfFinalHeaderItem.clear();
        listOfFinalHeaderItem.addAll(stringList);
    }

    public static String getNextWord(String text, String finditsNext) {
        String[] words = text.split(" "), data = finditsNext.split(" ");
        int index = Arrays.asList(words).indexOf((data.length > 1) ? data[data.length - 1] : data[0]);
        return (index == -1) ? "" : ((index + 1) == words.length) ? "End" : words[index + 1];
    }

    public static String[] getItem(String string) {
        return string.trim().toUpperCase(Locale.ROOT).split("\\|");
    }

    public static boolean isValidTemplate(String input) {
        String[] data = getItem(input);
        if (data.length > 1) {
            return true;
        }
        return false;
    }

    public static LinkedHashMap<String, String> geVendorColumnName(String input) {
        String[] listOfVendor = input.trim().toUpperCase().split("\\|");
        for (int i = 0; i < listOfVendor.length; i++) {
            vendorItem.put(listOfVendor[i], "");
        }
        return vendorItem;
    }

    public static String findTextBetween(String input, String word1, String word2) {
        try {
            return input.substring(input.indexOf(word1) + word1.length(),
                    input.indexOf(word2));
        } catch (Exception e) {
            return "";
        }

    }

    public static String containsWords(String inputString, String[] items) {
        for (String item : items) {
            if (inputString.contains(item)) {
                return item;
            }

        }
        return null;
    }
}


