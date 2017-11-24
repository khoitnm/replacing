package org.tnmk.replacing.all.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;

/**
 * Version: 1.0.0
 */
public final class ExcelOperatorUtils {
    private ExcelOperatorUtils() {
        //Utils
    }

    /**
     * Copy from https://stackoverflow.com/questions/21038935/how-to-shift-column-in-java-xssf-poi
     *
     * @param row
     * @param originalIndex
     * @param shiftCount
     */
    @Deprecated
    public static void shiftColumns(Row row, int originalIndex, int shiftCount) {
        for (int i = row.getPhysicalNumberOfCells() - 1; i >= originalIndex; i--) {
            Cell oldCell = row.getCell(i);
            Cell newCell = row.createCell(i + shiftCount, oldCell.getCellTypeEnum());
            cloneCellValue(oldCell, newCell);
            //Will have bug with formular columns.
        }
    }

    @Deprecated
    public static void cloneCellValue(Cell oldCell, Cell newCell) { //TODO test it
        switch (oldCell.getCellTypeEnum()) {
            case STRING:
                newCell.setCellValue(oldCell.getStringCellValue());
                break;
            case NUMERIC:
                newCell.setCellValue(oldCell.getNumericCellValue());
                break;
            case BOOLEAN:
                newCell.setCellValue(oldCell.getBooleanCellValue());
                break;
            case FORMULA:
                newCell.setCellFormula(oldCell.getCellFormula());
                break;
            case ERROR:
                newCell.setCellErrorValue(oldCell.getErrorCellValue());
            case BLANK:
            case _NONE:
                break;
        }
    }

    public static int countRows(Sheet sheet) {
        return sheet.getLastRowNum() + 1;
    }

    /**
     * Copy from https://github.com/bit-twit/poi-shift-column/blob/master/src/main/java/org/bittwit/poi/ExcelOpener.java
     *
     * @param workbook
     * @param sheet
     * @param columnIndex
     */
    public static void insertColumn(Workbook workbook, Sheet sheet, int columnIndex) {
        assert workbook != null;

        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        evaluator.clearAllCachedResultValues();

        int nrRows = countRows(sheet);
        int nrCols = countColumns(sheet);

        for (int row = 0; row < nrRows; row++) {
            Row r = sheet.getRow(row);

            if (r == null) {
                continue;
            }

            // shift to right
            for (int col = nrCols; col > columnIndex; col--) {
                Cell rightCell = r.getCell(col);
                if (rightCell != null) {
                    r.removeCell(rightCell);
                }

                Cell leftCell = r.getCell(col - 1);

                if (leftCell != null) {
                    Cell newCell = r.createCell(col, leftCell.getCellTypeEnum());
                    cloneCell(newCell, leftCell);
                    if (newCell.getCellTypeEnum() == CellType.FORMULA) {
                        newCell.setCellFormula(ExcelHelper.updateFormula(newCell.getCellFormula(), columnIndex));
                        evaluator.notifySetFormula(newCell);
                        CellValue cellValue = evaluator.evaluate(newCell);
                        evaluator.evaluateFormulaCellEnum(newCell);
                        System.out.println(cellValue);
                    }
                }
            }

            // delete old column
            CellType cellType = CellType.BLANK;

            Cell currentEmptyWeekCell = r.getCell(columnIndex);
            if (currentEmptyWeekCell != null) {
//				cellType = currentEmptyWeekCell.getCellType();
                r.removeCell(currentEmptyWeekCell);
            }

            // create new column
            r.createCell(columnIndex, cellType);
        }

        // Adjust the column widths
        for (int col = nrCols; col > columnIndex; col--) {
            sheet.setColumnWidth(col, sheet.getColumnWidth(col - 1));
        }

        // currently updates formula on the last cell of the moved column
        // TODO: update all cells if their formulas contain references to the moved cell
//		Row specialRow = sheet.getRow(nrRows-1);
//		Cell cellFormula = specialRow.createCell(nrCols - 1);
//		cellFormula.setCellType(XSSFCell.CELL_TYPE_FORMULA);
//		cellFormula.setCellFormula(formula);

        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
    }

    /*
     * Takes an existing Cell and merges all the styles and formula into the new one
     */
    private static void cloneCell(Cell newCell, Cell oldCell) {
        newCell.setCellComment(oldCell.getCellComment());
        newCell.setCellStyle(oldCell.getCellStyle());

        switch (oldCell.getCellTypeEnum()) {
            case BOOLEAN: {
                newCell.setCellValue(oldCell.getBooleanCellValue());
                break;
            }
            case NUMERIC: {
                newCell.setCellValue(oldCell.getNumericCellValue());
                break;
            }
            case STRING: {
                newCell.setCellValue(oldCell.getStringCellValue());
                break;
            }
            case ERROR: {
                newCell.setCellValue(oldCell.getErrorCellValue());
                break;
            }
            case FORMULA: {
                newCell.setCellFormula(oldCell.getCellFormula());
                break;
            }
            case BLANK:
            case _NONE:
                break;
        }
    }

    public static int countColumns(Sheet sheet) {
        int lastCol = 0;
        for (Row row : sheet) {
            int lastColInRow = row.getLastCellNum();
            if (lastColInRow > lastCol) {
                lastCol = lastColInRow;
            }
        }
        return lastCol;
    }
}
