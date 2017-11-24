package org.tnmk.replacing.all.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class ScoutDataProcessingService {
    private static final int ROW_DATA_HEADER = 1;

    private static final int ROW_DATA_CONTENT = 2;
    private static final int COL_CP = 4;
    private static final int COL_PP = 5;
    private static final int COL_AP = 6;
    private static final int COL_POTENTIAL_RATE = 7;

    public void processCsvToXlsx(String csvAbsFileName, String xlsxAbsFileName) {
        XSSFWorkbook workbook = ExcelIOUtils.readCsvAsXlsx(csvAbsFileName, ",");
        process(workbook);
        ExcelIOUtils.writeToFile(workbook, xlsxAbsFileName);
    }

    private void process(XSSFWorkbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);

        calculateAP(workbook, sheet);
        calculatePotentialRate(workbook, sheet);

        applyHeaderFilter(sheet);
        changeHeaderStyle(workbook, sheet);
    }

    private void changeHeaderStyle(XSSFWorkbook workbook, Sheet sheet) {
        CellStyle cellStyle = ExcelStyleUtils.newCellStyle(workbook, Color.WHITE, new Color(106, 44, 114));
        ExcelStyleUtils.applyStyleToRows(sheet, cellStyle, 0, 1);
    }


    /**
     * Insert column: Availability Point (AP = Potential P - Current P)
     *
     * @param workbook
     * @param sheet
     */
    private void calculateAP(Workbook workbook, Sheet sheet) {
        insertColumn(workbook, sheet, COL_AP, "AP", "Firow-Eirow");
    }

    private void calculatePotentialRate(Workbook workbook, Sheet sheet) {
        insertColumn(workbook, sheet, COL_POTENTIAL_RATE, "Potential Rate", "Kirow+Girow*$B$1/10/100");
    }

    /**
     * This method is specific for this Excel file only.
     *
     * @param workbook
     * @param sheet
     * @param colIndex
     * @param header
     * @param formulaPattern
     */
    private void insertColumn(Workbook workbook, Sheet sheet, int colIndex, String header, String formulaPattern) {
        ExcelOperatorUtils.insertColumn(workbook, sheet, colIndex);

        ExcelValueUtils.setString(sheet, ROW_DATA_HEADER, colIndex, header);
        int numRow = ExcelOperatorUtils.countRows(sheet);
        for (int irow = ROW_DATA_CONTENT; irow < numRow; irow++) {
            String formula = formulaPattern.replaceAll("irow", "" + (irow + 1));
            ExcelValueUtils.setFormula(sheet, irow, colIndex, formula);
        }
    }

    private void applyHeaderFilter(Sheet sheet) {
        sheet.setAutoFilter(new CellRangeAddress(ROW_DATA_HEADER, ExcelOperatorUtils.countRows(sheet) - 1, 0, getLastCol(sheet)));
    }

    private int getLastCol(Sheet sheet) {
        return sheet.getRow(ROW_DATA_HEADER).getLastCellNum();
    }
}
