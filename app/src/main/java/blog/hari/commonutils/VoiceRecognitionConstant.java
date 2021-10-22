package blog.hari.commonutils;

import static blog.hari.commonutils.TemplateInput.getItem;
import static blog.hari.commonutils.TemplateInput.getNextWord;
import static blog.hari.commonutils.TemplateInput.listOfFinalHeaderItem;
import static blog.hari.commonutils.TemplateInput.vendorItem;

import java.util.ArrayList;
import java.util.Map;

public class VoiceRecognitionConstant {

    public static String getTemplateText() {
        String[] listOfTitle = {"title", "serial", "item", "specification", "size 1", "size 2", "grade", "unit"};

        String finalData = "";

        for (int i = 0; i < listOfTitle.length; i++) {
            finalData = finalData + listOfTitle[i] + " |";
        }

        for (int i = 10; i < listOfFinalHeaderItem.size(); i++) {
            finalData = finalData + listOfFinalHeaderItem.get(i) + " |";
        }
        return finalData;
    }

    public static String getInputData(String inputString) {
//        inputString = "create title fresh vegetable serial 1 item carrot specification fresh,mh size 1 grade A  unit Kg satish 4 sagar 5";
        String[] listOfTitle = {"title", "serial", "item", "specification", "size ", "sizeb 2", "grade", "unit"};
        String finalData = "";
        ArrayList<String> listOfItem = new ArrayList<>();

        for (String item : listOfTitle) {
            if (inputString.toLowerCase().contains(item)) {
                if (item.contains("specification")) {
                    finalData = finalData + findTextBetween(inputString.toLowerCase(), "specification", "size") + " |";
                    listOfItem.add(findTextBetween(inputString.toLowerCase(), "specification", "grade"));
                }
                else if(item.contains("title")){
                    finalData = finalData + findTextBetween(inputString.toLowerCase(), "title", "serial") + " |";
                    listOfItem.add(findTextBetween(inputString.toLowerCase(), "title", "serial"));
                }
                else {
                    finalData = finalData + getNextWord(inputString.toLowerCase(), item) + " |";
                    listOfItem.add(getNextWord(inputString.toLowerCase(), item));
                }
            } else {
                finalData = finalData + "" + " |";
                listOfItem.add("");
            }
        }

        for (Map.Entry<String, String> it :
                vendorItem.entrySet()) {
            if (inputString.contains(it.getKey().toLowerCase())) {
                finalData = finalData + getNextWord(inputString, it.getKey().toLowerCase()) + " |";
            } else {
                finalData = finalData + "" + " |";
            }
        }

        return finalData;
    }

    private static String getFirstWord(String text) {

        int index = text.indexOf(' ');

        if (index > -1) { // Check if there is more than one word.

            return text.substring(0, index).trim(); // Extract first word.

        } else {

            return text; // Text is the first word itself.
        }
    }

    public static String findTextBetween(String input, String word1, String word2) {
        try {
            return input.substring(input.indexOf(word1) + word1.length(),
                    input.indexOf(word2));
        } catch (Exception e) {
            return "";
        }

    }
    public static boolean isValidInput(String inputString) {
        String[] data = getItem(inputString);
        int inputLength = 8+vendorItem.size();
        if (data.length == inputLength &&
                isNumeric(data[1].trim()) &&
                !data[1].isEmpty() &&
                !data[2].trim().equals("") &&
                !data[3].trim().equals("") &&
                !data[4].trim().equals("") &&
                !data[7].trim().equals("")) {
            return true;
        }
        return false;

    }
    public static boolean isNumeric(String str) {
        try {
            int number = Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
