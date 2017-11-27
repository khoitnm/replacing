package org.tnmk.replacing.all.scoutdata;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.excel.ExcelIOUtils;
import org.tnmk.replacing.all.excel.ExcelOperatorUtils;
import org.tnmk.replacing.all.excel.ExcelStyleUtils;
import org.tnmk.replacing.all.excel.ExcelValueUtils;
import org.tnmk.replacing.all.renaming.TraverseFolderService;
import org.tnmk.replacing.all.util.DateTimeUtils;
import org.tnmk.replacing.all.util.FileUtils;

import java.awt.*;
import java.io.File;

import static org.tnmk.replacing.all.scoutdata.ScoutDataHelper.ROW_DATA_HEADER;
import static org.tnmk.replacing.all.scoutdata.ScoutDataHelper.findColumnWithHeader;

@Service
public class ScoutDataProcessingService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ScoutDataProcessingService.class);


    @Autowired
    private ScoutDataPaintingService scoutDataPaintingService;

    @Autowired
    private ScoutDataRemoveLowPotentialService removeLowPotentialService;

    @Autowired
    private TraverseFolderService traverseFolderService;

    public void processCsvToXlsx(String rootFolderPath) {
        File file = new File(rootFolderPath);
        this.traverseFolderService.traverFile(file, currentFile -> {
            if (isCsvFile(currentFile)) {
                String targetFileName = currentFile + "_" + DateTimeUtils.formatLocalDateTimeForFilePath() + "_.xlsx";
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
        process(workbook, xlsxAbsFileName);
        ExcelIOUtils.writeToFile(workbook, xlsxAbsFileName);
    }

    private void process(XSSFWorkbook workbook, String xlsxAbsFileName) {

        Sheet sheet = workbook.getSheetAt(0);

        calculateAP(sheet);
        calculatePotentialRate(sheet);
//        this.removeLowPotentialService.removeLowPotentialRate(sheet);
//        ExcelIOUtils.writeToFile(workbook, xlsxAbsFileName);
        LOGGER.info("Calculated potential rate " + xlsxAbsFileName);

        rearrangeColumns(sheet);
//        ExcelIOUtils.writeToFile(workbook, xlsxAbsFileName);
        LOGGER.info("Rearranged columns " + xlsxAbsFileName);


        applyHeaderFilter(sheet);
        changeHeaderStyle(sheet);
        this.scoutDataPaintingService.paintColumnsByValues(sheet);
//        ExcelIOUtils.writeToFile(workbook, xlsxAbsFileName);
        LOGGER.info("Applying styles " + xlsxAbsFileName);


        sheet.createFreezePane(ScoutDataHelper.COL_NAME + 1, ScoutDataHelper.ROW_DATA_CONTENT);
//        ExcelIOUtils.writeToFile(workbook, xlsxAbsFileName);
        LOGGER.info("Finished!!! " + xlsxAbsFileName);

    }

    private void changeHeaderStyle(Sheet sheet) {
        XSSFWorkbook workbook = (XSSFWorkbook) sheet.getWorkbook();
        XSSFCellStyle cellStyle = ExcelStyleUtils.newCellStyle(workbook, Color.WHITE, new Color(106, 44, 114));
        cellStyle.getFont().setBold(true);
        ExcelStyleUtils.applyStyleToRows(sheet, cellStyle, 0, 1);
    }


    /**
     * Insert column: Availability Point (AP = Potential P - Current P)
     *
     * @param sheet
     */
    private void calculateAP(Sheet sheet) {
        int colPotAbilityIndex = findColumnWithHeader(sheet, ScoutDataHelper.HEADER_POT_ABILITY);
        int colAvailableAbilityIndex = colPotAbilityIndex + 1;
        insertColumn(sheet, colAvailableAbilityIndex, "AP", "Firow-Eirow");
    }

    private void calculatePotentialRate(Sheet sheet) {
        int colPotAbilityIndex = findColumnWithHeader(sheet, ScoutDataHelper.HEADER_POT_ABILITY);
        int colPotentialRate = colPotAbilityIndex + 2;
        insertColumn(sheet, colPotentialRate, ScoutDataHelper.HEADER_POTENTIAL_RATE, "Kirow+Girow*$B$1/10/100");
        stylePercentage(sheet, colPotentialRate);
    }

    /**
     * This method is specific for this Excel file only.
     *
     * @param sheet
     * @param colIndex
     * @param header
     * @param formulaPattern
     */
    private void insertColumn(Sheet sheet, int colIndex, String header, String formulaPattern) {
        ExcelOperatorUtils.insertColumn(sheet, colIndex);

        ExcelValueUtils.setString(sheet, ROW_DATA_HEADER, colIndex, header);
        int numRow = ExcelOperatorUtils.countRows(sheet);
        for (int irow = ScoutDataHelper.ROW_DATA_CONTENT; irow < numRow; irow++) {
            String formula = formulaPattern.replaceAll("irow", "" + (irow + 1));
            ExcelValueUtils.setFormula(sheet, irow, colIndex, formula);
        }
    }

    private void stylePercentage(Sheet sheet, int colIndex) {
        Workbook workbook = sheet.getWorkbook();
        CellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0%"));

        int numRow = ExcelOperatorUtils.countRows(sheet);
        for (int irow = ScoutDataHelper.ROW_DATA_CONTENT; irow < numRow; irow++) {
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

    private void rearrangeColumns(Sheet sheet) {
        arrangeColumns(sheet,
                "Acceleration", "Pace", "Jumping", "Stamina", "Strength"
                , "Tackling", "Marking", "Positioning", "Head"
                , "Finishing", "Long shot", "Off The Ball"
                , "Dribbling", "Technique"
                , "Passing", "Creativity", "Crossing"
                , "Handling", "Reflexes", "One On Ones"
        );
    }

    private void arrangeColumns(Sheet sheet, String... columnHeaders) {
        String firstColumnHeader = columnHeaders[0];
        int firstColumnIndex = findColumnWithHeader(sheet, firstColumnHeader);
        for (int i = 1; i < columnHeaders.length; i++) {
            String nextColumnHeader = columnHeaders[i];
            LOGGER.info("Moving column '{}'...", nextColumnHeader);
            int nextColIndex = findColumnWithHeader(sheet, nextColumnHeader);
            if (nextColIndex == -1) {
                LOGGER.warn("Cannot found column '{}' in the sheet '{}'", nextColumnHeader, sheet);
                continue;
            }
            ExcelOperatorUtils.moveColumn(sheet, nextColIndex, firstColumnIndex + i);
        }
    }

}
