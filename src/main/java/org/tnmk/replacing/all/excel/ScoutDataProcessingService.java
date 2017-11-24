package org.tnmk.replacing.all.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class ScoutDataProcessingService {
    public void processCsvToXlsx(String csvAbsFileName, String xlsxAbsFileName) {
        XSSFWorkbook workbook = ExcelIOUtils.readCsvAsXlsx(csvAbsFileName, ",");
        process(workbook);
        ExcelIOUtils.writeToFile(workbook, xlsxAbsFileName);
    }

    private void process(XSSFWorkbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        changeHeaderStyle(workbook, sheet);
    }

    private void changeHeaderStyle(XSSFWorkbook workbook, Sheet sheet) {
        CellStyle cellStyle = ExcelStyleUtils.newCellStyle(workbook, Color.WHITE, new Color(106, 44, 114));
        ExcelStyleUtils.applyStyleToRows(sheet, cellStyle, 0, 1);
    }
}
