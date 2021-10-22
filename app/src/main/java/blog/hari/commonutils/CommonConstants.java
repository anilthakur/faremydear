package blog.hari.commonutils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class CommonConstants {

    public static  String[] TEMPLATE_SEARCH_KEYWORD = {"template", "templat"} ;
    public static String[]  COLUMN_SEARCH_KEYWORD ={"column","colum","columnn"};


    public static List<String> getHeaderTitle(LinkedHashMap<String,String> vendor) {
        List listOfHeaderItem = new ArrayList<String>();
        listOfHeaderItem.add("Sr No ");
        listOfHeaderItem.add("Item");
        listOfHeaderItem.add("Specification");
        listOfHeaderItem.add("Size 1 BR");
        listOfHeaderItem.add("Size 2 SR ");
        listOfHeaderItem.add("GRADE");
        listOfHeaderItem.add("Unit ");
        listOfHeaderItem.add("Quantity(R0)");
        listOfHeaderItem.add(" Contingencies(10%)");
        listOfHeaderItem.add("Total Quantity");
        for (Map.Entry<String, String> entry : vendor.entrySet()) {
            listOfHeaderItem.add(entry.getKey());
        }

        return listOfHeaderItem;
    }



    public static LinkedHashMap<String, List<Entities>> getExcelData2(LinkedHashMap<String,String> vendor) {
        LinkedHashMap<String, List<Entities>> excelData = new LinkedHashMap<>();
        List freshVEGITABLEItem = new ArrayList<Entities>();
        List freeItems = new ArrayList<Entities>();
        List dairyItem = new ArrayList<Entities>();
        vendor.put("Satish","7");
        vendor.put("Sagar","");

        Entities carrots = new Entities("1", "CARROTS", "FRESH , ORGANIC, MH", "3", "", "", "KG", "6", "0", "3",vendor);
        Entities freshVegetableItem = new Entities();
        freshVegetableItem.setSpecification("Fresh Vegetable");
        freshVegetableItem.setVendors(vendor);
        freshVEGITABLEItem.add(freshVegetableItem);
        freshVEGITABLEItem.add(carrots);

        Entities tuna = new Entities("2", "TUNA", "FRESH , ORGANIC, MH", "3", "", "", "KG", "4", "0", "2", vendor);
        Entities oranges = new Entities("3", "ORENGES", "FRESH , ORGANIC, MH", "3", "", "", "KG", "9", "0", "5",vendor);
        Entities freeItem = new Entities();
        freeItem.setSpecification("Free Item");
        freeItem.setVendors(vendor);
        freeItems.add(freeItem);
        freeItems.add(tuna);
        freeItems.add(oranges);

        Entities dairy = new Entities("1", "MILK", "FRESH , HYBRID, MH", "3", "", "", "KG", "6", "0", "9",vendor);
        Entities dairyTitle = new Entities();
        dairyTitle.setSpecification("DAIRY");
        dairyTitle.setVendors(vendor);
        dairyItem.add(dairyTitle);
        dairyItem.add(dairy);

        excelData.put("Fresh Vegetable", freshVEGITABLEItem);
        excelData.put("DAIRY", dairyItem);
        excelData.put("Free Item", freeItems);
        return excelData;
    }
}
