package com.example.hjh.utils;

import org.apache.poi.xssf.usermodel.XSSFCell;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExcelUtils {
    public static String getStringValue(XSSFCell cell){
        return  getStringValue(cell, "");
    }

    public static  String getStringValue(XSSFCell cell, String defaultValue){
        if ( cell==null ){
            return defaultValue;
        }

        String result = defaultValue;
        switch ( cell.getCellType() ) {
            case XSSFCell.CELL_TYPE_NUMERIC:
                result = new BigDecimal(cell.getNumericCellValue()).toPlainString();
                break;
            case XSSFCell.CELL_TYPE_STRING :
                result = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                boolean bResult = cell.getBooleanCellValue();
                result = String.valueOf(bResult);
                break;
            case XSSFCell.CELL_TYPE_FORMULA:
            case XSSFCell.CELL_TYPE_BLANK:
            case XSSFCell.CELL_TYPE_ERROR:
            default:
                break;
        }

        return result;
    }

    public static Date getDategValue(XSSFCell cell){
        if ( cell==null ){
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        String result = "";
        switch ( cell.getCellType() ) {
            case XSSFCell.CELL_TYPE_NUMERIC:
                double dValue = cell.getNumericCellValue();
                calendar.set(1899, 11, 30);
                calendar.add(Calendar.DAY_OF_YEAR, (int)dValue);
                return  calendar.getTime();
            case XSSFCell.CELL_TYPE_STRING :
                result = cell.getStringCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return  sdf.parse(result);
                } catch (ParseException e) {
                    e.printStackTrace();
                    sdf = new SimpleDateFormat("yyyy/MM/dd");
                    try {
                        return  sdf.parse(result);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            case XSSFCell.CELL_TYPE_FORMULA:
            case XSSFCell.CELL_TYPE_BLANK:
            case XSSFCell.CELL_TYPE_BOOLEAN:
            default:
                break;
        }

        return null;
    }
}
