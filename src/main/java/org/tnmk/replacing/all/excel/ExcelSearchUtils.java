package org.tnmk.replacing.all.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Locale;

public final class ExcelSearchUtils {
    private ExcelSearchUtils() {
        //Utils
    }

    /**
     * @param sheet
     * @param findingRowIndex
     * @param columnValue
     * @return if not found, return -1
     */
    public static int findFirstColumnIndexOnRow(Sheet sheet, int findingRowIndex, String columnValue) {
        Row row = sheet.getRow(findingRowIndex);
        String columnValueLowercase = columnValue.toLowerCase(Locale.ENGLISH);
        int i = 0;
        for (Cell cell : row) {
            String cellValue = cell.getStringCellValue();
            if (cellValue.toLowerCase().contains(columnValueLowercase)) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
