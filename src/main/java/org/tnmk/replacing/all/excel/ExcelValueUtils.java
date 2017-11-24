package org.tnmk.replacing.all.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public final class ExcelValueUtils {
    private ExcelValueUtils() {
        //Utils
    }

    public static void setString(Sheet sheet, int rowIndex, int columnIndex, String string) {
        Row row = sheet.getRow(rowIndex);
        Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        cell.setCellValue(string);
    }

    public static void setFormula(Sheet sheet, int rowIndex, int columnIndex, String formula) {
        Row row = sheet.getRow(rowIndex);
        Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//        cell.setCellType(CellType.FORMULA);
        cell.setCellFormula(formula);
    }
}
