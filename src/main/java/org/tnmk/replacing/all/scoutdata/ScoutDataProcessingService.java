package org.tnmk.replacing.all.scoutdata;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.excel.ExcelIOUtils;
import org.tnmk.replacing.all.excel.ExcelOperatorUtils;
import org.tnmk.replacing.all.excel.ExcelStyleUtils;
import org.tnmk.replacing.all.excel.ExcelValueUtils;
import org.tnmk.replacing.all.renaming.TraverseFolderService;
import org.tnmk.replacing.all.util.FileUtils;

import java.awt.Color;
import java.io.File;

@Service
public class ScoutDataProcessingService {
    private static final int ROW_DATA_HEADER = 1;

    private static final int ROW_DATA_CONTENT = 2;
    private static final int COL_NAME = 0;
    private static final int COL_CP = 4;
    private static final int COL_PP = 5;
    private static final int COL_AP = 6;
    private static final int COL_POTENTIAL_RATE = 7;

    @Autowired
    private TraverseFolderService traverseFolderService;

    public void processCsvToXlsx(String rootFolderPath) {
        File file = new File(rootFolderPath);
        this.traverseFolderService.traverFile(file, currentFile -> {
            if (isCsvFile(currentFile)) {
                String targetFileName = currentFile + ".xlsx";
                processCsvToXlsx(currentFile.getAbsolutePath(), targetFileName);
            }
            return currentFile;
        });
    }

    private boolean isCsvFile(File file) {
        if (!file.isFile()) return false;
        String fileExtension = FileUtils.getFileExtension(file.getAbsolutePath());
        return "csv".equalsIgnoreCase(fileExtension);
    }

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
        sheet.createFreezePane(COL_NAME + 1, ROW_DATA_CONTENT);
    }

    private void changeHeaderStyle(XSSFWorkbook workbook, Sheet sheet) {
        XSSFCellStyle cellStyle = ExcelStyleUtils.newCellStyle(workbook, Color.WHITE, new Color(106, 44, 114));
        cellStyle.getFont().setBold(true);
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
        stylePercentage(workbook, sheet, COL_POTENTIAL_RATE);
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

    private void stylePercentage(Workbook workbook, Sheet sheet, int colIndex) {
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0%"));

        int numRow = ExcelOperatorUtils.countRows(sheet);
        for (int irow = ROW_DATA_CONTENT; irow < numRow; irow++) {
            Cell cell = sheet.getRow(irow).getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellStyle(style);
        }
    }

    private void applyHeaderFilter(Sheet sheet) {
        sheet.setAutoFilter(new CellRangeAddress(ROW_DATA_HEADER, ExcelOperatorUtils.countRows(sheet) - 1, 0, getLastCol(sheet)));
    }

    private int getLastCol(Sheet sheet) {
        return sheet.getRow(ROW_DATA_HEADER).getLastCellNum();
    }
}
