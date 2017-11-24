package org.tnmk.replacing.all.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Color;

public class ExcelStyleUtils {
    public static XSSFCellStyle newBackgroundColorStyle(XSSFWorkbook workbook, Color color) {
        XSSFCellStyle style = workbook.createCellStyle();
        applyBackgroundColor(style, color);
        return style;
    }

    public static XSSFCellStyle newTextColorStyle(XSSFWorkbook workbook, Color textColor) {
        XSSFCellStyle textStyle = workbook.createCellStyle();
        applyTextColor(workbook, textStyle, textColor);
        return textStyle;
    }

    public static XSSFCellStyle newCellStyle(XSSFWorkbook workbook, Color textColor, Color backgroundColor) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        applyTextColor(workbook, cellStyle, textColor);
        applyBackgroundColor(cellStyle, backgroundColor);
        return cellStyle;
    }

    public static void applyTextColor(XSSFWorkbook workbook, XSSFCellStyle cellStyle, Color textColor) {
        XSSFFont textFont = workbook.createFont();
        textFont.setColor(new XSSFColor(textColor));
        cellStyle.setFont(textFont);
    }

    public static XSSFCellStyle applyBackgroundColor(XSSFCellStyle style, Color color) {
        style.setFillForegroundColor(new XSSFColor(color));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    public static void applyStyleToRows(Sheet sheet, CellStyle cellStyle, int... rowIndexes) {
        for (int rowIndex : rowIndexes) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                applyStyleToRow(cellStyle, row);
            }
        }
    }

    public static void applyStyleToRow(CellStyle cellStyle, Row row) {
        for (Cell cell : row) {
            cell.setCellStyle(cellStyle);
        }
    }
}
