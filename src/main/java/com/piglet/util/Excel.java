package com.piglet.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Excel {

    private static DecimalFormat df = new DecimalFormat("0");
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DecimalFormat nf = new DecimalFormat("0.00");

    public static List<ArrayList<Object>> readExcel2003(InputStream is) {
        try {
            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
            ArrayList<Object> colList;
            HSSFWorkbook wb = new HSSFWorkbook(is);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row;
            HSSFCell cell;
            Object value = null;
            for (int i = sheet.getFirstRowNum() + 1, rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                colList = new ArrayList<Object>();
                if (row == null) {
                    //鍒ゆ柇鏄惁鏄渶鍚庝竴琛
                    if (i != sheet.getPhysicalNumberOfRows()) {
                        rowList.add(colList);
                    }
                    return rowList;
                } else {
                    rowCount++;
                }
                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        if (j != row.getLastCellNum()) {
                            colList.add("");
                        }
                        continue;
                    }
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                                    break;
                                } else {
                                    Double d = cell.getNumericCellValue();
                                    value = nf.format(d);
                                }
                                break;
                            case HSSFCell.CELL_TYPE_STRING:
                                value = cell.getStringCellValue();
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                value = cell.getBooleanCellValue() + "";
                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                value = cell.getCellFormula() + "";
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                value = "";
                                break;
                            case HSSFCell.CELL_TYPE_ERROR:
                                value = "非法字符";
                                break;
                            default:
                                value = "未知类型";
                                break;
                        }
                    }
                    colList.add(value);
                }
                rowList.add(colList);
            }
            if (is != null) {
                is.close();
            }
            return rowList;
        } catch (Exception e) {
            return null;
        }
    }
    public static List<ArrayList<Object>> readExcel2007(InputStream is) {
        try {
            ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
            ArrayList<Object> colList;
            XSSFWorkbook wb = new XSSFWorkbook(is);
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;
            Object value = null;
            for (int i = sheet.getFirstRowNum() + 1, rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                colList = new ArrayList<Object>();
                if (row == null) {
                    //鍒ゆ柇鏄惁鏄渶鍚庝竴琛
                    if (i != sheet.getPhysicalNumberOfRows()) {
                        rowList.add(colList);
                    }
                    return rowList;
                } else {
                    rowCount++;
                }
                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                        if (j != row.getLastCellNum()) {
                            colList.add("");
                        }
                        continue;
                    }
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                                    break;
                                } else {
                                    Double d = cell.getNumericCellValue();
                                    value = nf.format(d);
                                }
                                break;
                            case HSSFCell.CELL_TYPE_STRING:
                                value = cell.getStringCellValue();
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                value = cell.getBooleanCellValue() + "";
                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                value = cell.getCellFormula() + "";
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                value = "";
                                break;
                            case HSSFCell.CELL_TYPE_ERROR:
                                value = "非法字符";
                                break;
                            default:
                                value = "未知类型";
                                break;
                        }
                    }
                    colList.add(value);
                }
                rowList.add(colList);
            }
            if (is != null) {
                is.close();
            }
            return rowList;
        } catch (Exception e) {
            System.out.println("exception");
            return null;
        }
    }

    public static DecimalFormat getDf() {
        return df;
    }

    public static void setDf(DecimalFormat df) {
        Excel.df = df;
    }

    public static SimpleDateFormat getSdf() {
        return sdf;
    }

    public static void setSdf(SimpleDateFormat sdf) {
        Excel.sdf = sdf;
    }

    public static DecimalFormat getNf() {
        return nf;
    }

    public static void setNf(DecimalFormat nf) {
        Excel.nf = nf;
    }
}
