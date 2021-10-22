package blog.hari.commonutils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class CommonExcelFile {

    public static final String TAG = "ExcelUtil";
    private static Cell cell;
    private static Sheet sheet;
    private static Workbook workbook;
    private static CellStyle headerCellStyle;
    private static CellStyle subHeaderCellStyle;
    private static CellStyle valueCellStyle;
    public static boolean exportDataIntoWorkbook1(Context context, String fileName, List<String> listOfHeaderItem,
                                                  LinkedHashMap<String, List<Entities>> dataList) {
        boolean isWorkbookWrittenIntoStorage;

        // Check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return false;
        }

        // Creating a New HSSF Workbook (.xls format)
        workbook = new HSSFWorkbook();

        setHeaderCellStyle();

        sheet = workbook.createSheet("My Data Excel");
        for(int i =0;i<listOfHeaderItem.size();i++){
            sheet.setColumnWidth(i, (15 * 400));
        }
        setHeaderRow(listOfHeaderItem);
        fillDataIntoExcel(dataList);

        isWorkbookWrittenIntoStorage = storeExcelInStorage(context, fileName);

        return isWorkbookWrittenIntoStorage;
    }


    private static void fillDataIntoExcel(LinkedHashMap<String, List<Entities>> dataList) {
        List excelData = new ArrayList<Entities>();
        int position = 0;

        for (Map.Entry<String, List<Entities>> entry : dataList.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
            if (entry.getValue().size() > 0) {
                for (int k = 0; k < entry.getValue().size(); k++) {
                    if (entry.getValue().get(k).getSpecification().equals(entry.getKey())) {
                        Row rowData = sheet.createRow(position + 1);
                        subHeaderValue(rowData, entry.getValue().get(k).getSpecification(),entry.getValue().get(k).getVendors());
                        excelData.add(entry.getValue());
                        position = position + 1;
                    } else {
                        Row neWRowData = sheet.createRow(position + 1);
                        newRowData(neWRowData, entry.getValue().get(k));
                        excelData.add(entry.getValue());
                        position = position + 1;
                    }

                }

            }


        }
        position = 0;
    }
    private static void subHeaderValue(Row rowData, String title ,LinkedHashMap<String,String> vendor) {
        subSubHeaderCellStyle();
//        Row rowData = sheet.createRow(i + 1);
        cell = rowData.createCell(0);
        cell.setCellStyle(subHeaderCellStyle);
        cell = rowData.createCell(1);
        cell.setCellStyle(subHeaderCellStyle);
        cell = rowData.createCell(2);
        cell.setCellValue(title);
        cell.setCellStyle(subHeaderCellStyle);
        cell = rowData.createCell(3);
        cell.setCellStyle(subHeaderCellStyle);
        cell = rowData.createCell(4);
        cell.setCellStyle(subHeaderCellStyle);
        cell = rowData.createCell(5);
        cell.setCellStyle(subHeaderCellStyle);
        cell = rowData.createCell(6);
        cell.setCellStyle(subHeaderCellStyle);
        cell = rowData.createCell(7);
        cell.setCellStyle(subHeaderCellStyle);
        cell = rowData.createCell(8);
        cell.setCellStyle(subHeaderCellStyle);
        cell = rowData.createCell(9);
        cell.setCellStyle(subHeaderCellStyle);
        int position = 9 ;
        for (Map.Entry<String, String> entry : vendor.entrySet()) {
            position ++;
            cell = rowData.createCell(position);
            cell.setCellStyle(subHeaderCellStyle);

        }

    }


    private static void newRowData(Row neWRowData, Entities dataList) {
        ValueCellStyle();
//        Row neWRowData = sheet.createRow(i + 1);
        // Create Cells for each row
        cell = neWRowData.createCell(0);
        cell.setCellValue(dataList.getSrNo());
        cell.setCellStyle(valueCellStyle);

        cell = neWRowData.createCell(1);
        cell.setCellValue(dataList.getItem());
        cell.setCellStyle(valueCellStyle);

        cell = neWRowData.createCell(2);
        cell.setCellValue(dataList.getSpecification());
        cell.setCellStyle(valueCellStyle);

        cell = neWRowData.createCell(3);
        cell.setCellValue(dataList.getSize1BR());
        cell.setCellStyle(valueCellStyle);

        cell = neWRowData.createCell(4);
        cell.setCellValue(dataList.getSize1BR());
        cell.setCellStyle(valueCellStyle);

        cell = neWRowData.createCell(5);
        cell.setCellValue(dataList.getGRADE());
        cell.setCellStyle(valueCellStyle);

        cell = neWRowData.createCell(6);
        cell.setCellValue(dataList.getUnit());
        cell.setCellStyle(valueCellStyle);

        cell = neWRowData.createCell(7);
        cell.setCellValue(dataList.getQuantity());
        cell.setCellStyle(valueCellStyle);

        cell = neWRowData.createCell(8);
        cell.setCellValue(dataList.getContingencies());
        cell.setCellStyle(valueCellStyle);

        cell = neWRowData.createCell(9);
        cell.setCellValue(dataList.getTotalQuantity());
        cell.setCellStyle(valueCellStyle);
        int position = 9;

        for (Map.Entry<String, String> entry : dataList.getVendors().entrySet()) {
            position ++;
            cell = neWRowData.createCell(position);
            cell.setCellValue(entry.getValue());
            cell.setCellStyle(valueCellStyle);
        }


    }

    private static boolean storeExcelInStorage(Context context, String fileName) {
        boolean isSuccess;
        File file = new File(context.getExternalFilesDir(null), fileName);
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            Log.e(TAG, "Writing file" + file);
            isSuccess = true;
        } catch (IOException e) {
            Log.e(TAG, "Error writing Exception: ", e);
            isSuccess = false;
        } catch (Exception e) {
            Log.e(TAG, "Failed to save file due to Exception: ", e);
            isSuccess = false;
        } finally {
            try {
                if (null != fileOutputStream) {
                    fileOutputStream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return isSuccess;
    }
    private static void setHeaderRow(List<String> listOfHeaderItem) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < listOfHeaderItem.size(); i++) {
            cell = headerRow.createCell(i);
            cell.setCellValue(listOfHeaderItem.get(i));
            cell.setCellStyle(headerCellStyle);
        }

    }

    private static void setHeaderCellStyle() {
        headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    }

    private static void subSubHeaderCellStyle() {
        subHeaderCellStyle = workbook.createCellStyle();
        subHeaderCellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        subHeaderCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        subHeaderCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    }

    private static void ValueCellStyle() {
        valueCellStyle = workbook.createCellStyle();
        valueCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    }



    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
