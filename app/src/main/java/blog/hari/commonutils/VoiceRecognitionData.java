package blog.hari.commonutils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class VoiceRecognitionData {

    public static LinkedHashMap<String, List<Entities>> getExcelData(List<String> input) {
        //FreshVegetable | 1 | Carrot | MH | 1|2|ABC|8|6|7
        LinkedHashMap<String, List<Entities>> mapData = new LinkedHashMap<>();
        List<Entities> listOfBean = new ArrayList();
        for (int i = 0; i < input.size(); i++) {
            if (!getAddedItem(mapData, input.get(i))) {
                Entities obj = new Entities();
                getAddedItem(mapData, input.get(i));
                obj.setSrNo(getItem(input.get(i))[1].trim());
                obj.setItem(getItem(input.get(i))[2]);
                obj.setSpecification(getItem(input.get(i))[3]);
                if (getItem(input.get(i))[4].isEmpty()) {
                    obj.setSize1BR(obj.Size1BR);
                } else {
                    obj.setSize1BR(getItem(input.get(i))[4]);
                }
                obj.setSize2BRR(getItem(input.get(i))[5]);
                obj.setGRADE(getItem(input.get(i))[6]);
                obj.setUnit(getItem(input.get(i))[7]);

                obj.setContingencies("10%");
                obj.setQuantity("1");
                obj.setTotalQuantity("1");
                obj.setVendors(geVendorValue(getListOfVendorValue(input.get(i))));
                if (!getItem(input.get(i))[0].isEmpty()) {
                    listOfBean = new ArrayList<>();
                    listOfBean.add(getEmptyBean(getItem(input.get(i))[0]));
                    mapData.put(getItem(input.get(i))[0], listOfBean);
                }
                listOfBean.add(obj);
                mapData.put(lastKey(mapData), listOfBean);
            }
        }

        return mapData;
    }

    public static String[] getItem(String string) {
        return string.trim().toUpperCase(Locale.ROOT).split("\\|");
    }

    public static List<String> getListOfVendorValue(String input) {
        List<String> listItem = new ArrayList<>();
        String[] vendor = input.trim().toUpperCase(Locale.ROOT).split("\\|");
        for (int i = 0; i < vendor.length; i++) {
            listItem.add(vendor[i]);
        }

        return listItem;
    }

    public static Boolean getAddedItem(LinkedHashMap<String, List<Entities>> mapData, String inputString) {
        Set<String> keys = mapData.keySet();
        Entities obj = null;

        // printing the elements of LinkedHashMap
        for (String key : keys) {
            System.out.println(key + " -- "
                    + mapData.get(key));
            for (int i = 0; i < mapData.get(key).size(); i++) {
                if (getItem(inputString)[2].equals(mapData.get(key).get(i).getItem()) &&
                        getItem(inputString)[3].equals(mapData.get(key).get(i).getSpecification()) &&
                        getItem(inputString)[4].equals(mapData.get(key).get(i).getSize1BR()) &&
                        getItem(inputString)[5].equals(mapData.get(key).get(i).getSize2BRR()) &&
                        getItem(inputString)[6].equals(mapData.get(key).get(i).getGRADE()) &&
                        getItem(inputString)[7].equals(mapData.get(key).get(i).getUnit())

                ) {
                    obj = mapData.get(key).get(i);
                    int quantity = Integer.parseInt(mapData.get(key).get(i).getQuantity());
                    obj.setQuantity("" + (quantity + 1));
                    obj.setTotalQuantity("" + (quantity + 1));
                    mapData.get(key).set(i, obj);
                    return true;
                }

            }
        }

        return false;

    }

    private static Entities getEmptyBean(String title) {
        LinkedHashMap map = new LinkedHashMap();
        return new Entities("", "", title, "", "", "", "", "", "", "", map);
    }


    public static String lastKey(LinkedHashMap<String, List<Entities>> lhm) {
        int count = 1;

        for (Map.Entry<String, List<Entities>> it :
                lhm.entrySet()) {

            if (count == lhm.size()) {

                System.out.println("Last Key-> " + it.getKey());
                System.out.println("Last Value-> " + it.getValue());
                return it.getKey();
            }
            count++;
        }
        return "";
    }

    public static LinkedHashMap<String, String> geVendorValue(List<String> input) {
        LinkedHashMap<String, String> vendors = new LinkedHashMap<>();
        int positionOfValue = 0;
        for (int i = 8; i < input.size(); i++) {
            positionOfValue = i + 2;
            vendors.put(TemplateInput.listOfFinalHeaderItem.get(positionOfValue), input.get(i));
        }
        return vendors;
    }
}
