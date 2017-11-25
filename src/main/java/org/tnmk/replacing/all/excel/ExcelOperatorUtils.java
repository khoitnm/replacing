package org.tnmk.replacing.all.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;

/**
 * Version: 1.0.0
 * TODO Below methods may not work well with formula values.
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
            cloneCell(oldCell, newCell);
            //Will have bug with formula columns.
        }
    }


    public static int countRows(Sheet sheet) {
        return sheet.getLastRowNum() + 1;
    }

    /**
     * Copy from https://github.com/bit-twit/poi-shift-column/blob/master/src/main/java/org/bittwit/poi/ExcelOpener.java
     *
     * @param sheet
     * @param insertingColIndex
     */
    public static void insertColumn(Sheet sheet, int insertingColIndex) {
        assert sheet != null;

        Workbook workbook = sheet.getWorkbook();
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        evaluator.clearAllCachedResultValues();

        int numRows = countRows(sheet);
        int numCols = countColumns(sheet);

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                continue;
            }

            // shift to right
            for (int colIndex = numCols - 1; colIndex >= insertingColIndex; colIndex--) {
                moveColumn(evaluator, row, colIndex, colIndex + 1);
            }

            // create new column
//            row.createCell(insertingColIndex, cellType);
        }

        // Adjust the column widths
        for (int colIndex = numCols - 1; colIndex >= insertingColIndex; colIndex--) {
            int newColIndex = colIndex + 1;
            sheet.setColumnWidth(newColIndex, sheet.getColumnWidth(colIndex));
        }

        // currently updates formula on the last cell of the moved column
        // TODO: update all cells if their formulas contain references to the moved cell
//		Row specialRow = sheet.getRow(nrRows-1);
//		Cell cellFormula = specialRow.createCell(nrCols - 1);
//		cellFormula.setCellType(XSSFCell.CELL_TYPE_FORMULA);
//		cellFormula.setCellFormula(formula);

        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
    }

    public static void copyColumn(Sheet sheet, int sourceColIndex, int targetColIndex) {
        assert sheet != null;

        Workbook workbook = sheet.getWorkbook();
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        evaluator.clearAllCachedResultValues();

        int numRows = countRows(sheet);
        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                continue;
            }
            cloneCell(row, sourceColIndex, targetColIndex);
        }
        XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
    }

    private static Cell cloneCell(Row row, int sourceColIndex, int targetColIndex) {
        Sheet sheet = row.getSheet();
        Cell sourceCell = row.getCell(sourceColIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        Cell targetCell = row.createCell(targetColIndex, sourceCell.getCellTypeEnum());
        cloneCell(sourceCell, targetCell);

        sheet.setColumnWidth(targetColIndex, sheet.getColumnWidth(sourceColIndex));
        return targetCell;
    }

    public static void moveColumn(Sheet sheet, int sourceColIndex, int targetColIndex) {
        if (targetColIndex == sourceColIndex) {
            return;
        }
        insertColumn(sheet, targetColIndex);
        if (targetColIndex < sourceColIndex) {
            sourceColIndex++;
        }
        copyColumn(sheet, sourceColIndex, targetColIndex);

        if (targetColIndex > sourceColIndex) {
            //columns from source to target - 1 will be shift to the left.
            for (int movingColIndex = sourceColIndex + 1; movingColIndex <= targetColIndex - 1; movingColIndex++) {
                copyColumn(sheet, movingColIndex, movingColIndex - 1);
                clearColumn(sheet, movingColIndex);
            }
        } else {
            int lastColIndex = countColumns(sheet) - 1;
            for (int movingColIndex = sourceColIndex + 1; movingColIndex <= lastColIndex; movingColIndex++) {
                copyColumn(sheet, movingColIndex, movingColIndex - 1);
                clearColumn(sheet, movingColIndex);
            }
        }
    }

    public static void clearColumn(Sheet sheet, int columnIndex) {
        for (Row row : sheet) {
            Cell cell = row.getCell(columnIndex);
            if (cell != null) {
                row.removeCell(cell);
            }
        }
    }

    /**
     * @param evaluator
     * @param row
     * @param sourceColIndex e.g. current col is 5
     * @param targetColIndex Note: the old data of the target column will be replaced by original column.
     */
    private static void moveColumn(FormulaEvaluator evaluator, Row row, int sourceColIndex, int targetColIndex) {

        Cell targetCell = row.getCell(targetColIndex);//The first targetCell is the empty cell.
        if (targetCell != null) {
            row.removeCell(targetCell);
        }

        Cell sourceCell = row.getCell(sourceColIndex);

        if (sourceCell != null) {
            targetCell = row.createCell(targetColIndex, sourceCell.getCellTypeEnum());
            cloneCell(sourceCell, targetCell);
            if (targetCell.getCellTypeEnum() == CellType.FORMULA) {
                //TODO also change the reference of other cols which is related to this col.
                String newCellFormula = ExcelHelper.updateFormula(targetCell.getCellFormula(), sourceColIndex, targetColIndex);
                targetCell.setCellFormula(newCellFormula);
                evaluator.notifySetFormula(targetCell);
                CellValue cellValue = evaluator.evaluate(targetCell);
                evaluator.evaluateFormulaCellEnum(targetCell);
                System.out.println(cellValue);
            }

            row.removeCell(sourceCell);
        }
    }

    /*
     * Takes an existing Cell and merges all the styles and formula into the new one
     */
    private static void cloneCell(Cell oldCell, Cell newCell) {
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
