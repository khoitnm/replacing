package org.tnmk.replacing.all.scoutdata;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.color.ColoringArrayService;
import org.tnmk.replacing.all.excel.ExcelStyleUtils;
import org.tnmk.replacing.all.util.ListUtils;
import org.tnmk.replacing.all.util.NumberUtils;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.tnmk.replacing.all.scoutdata.ScoutDataHelper.findColumnWithHeader;

@Service
public class ScoutDataPaintingService {

    @Autowired
    private ColoringArrayService coloringArrayService;

    public void paintColumnsByValues(Sheet sheet) {
        paintColumnsByValues(sheet,
                new Color(0, 194, 45),
                new Color(220, 255, 222),
                "Value"
        );
        paintColumnsByValues(sheet,
                new Color(180, 207, 247),
                new Color(106, 115, 153),
                "Acceleration", "Pace", "Jumping", "Stamina", "Strength"
        );
        paintColumnsByValues(sheet,
                new Color(247, 215, 236),
                new Color(235, 79, 220),
                "Tackling", "Marking", "Positioning", "Head"
        );
        paintColumnsByValues(sheet,
                new Color(217, 216, 247),
                new Color(96, 67, 180),
                "Finishing", "Long shot", "Off The Ball"
        );
        paintColumnsByValues(sheet,
                new Color(255, 247, 209),
                new Color(231, 180, 6),
                "Dribbling", "Technique"
        );
        paintColumnsByValues(sheet,
                new Color(224, 247, 228),
                new Color(16, 212, 0),
                "Passing", "Creativity", "Crossing"
        );
        paintColumnsByValues(sheet
                , new Color(247, 232, 211)
                , new Color(212, 127, 15)
                , "Handling", "Reflexes", "One On Ones"

        );
    }

    public void paintColumnsByValues(Sheet sheet, Color lowestColor, Color highestColor, String... columnsHeaders) {
        for (String header : columnsHeaders) {
            int colIndex = findColumnWithHeader(sheet, header);
            paintColumnByValues(sheet, colIndex, lowestColor, highestColor);
        }
    }

    private void paintColumnByValues(Sheet sheet, int colIndex, Color lowestColor, Color highestColor) {
        //key: row index, value: cell's number value
        Map<Integer, Double> rowNumberValues = getCellNumValueMaps(sheet, colIndex);
        ListUtils.ValuePosition<Double> highestPosition = ListUtils.findTop(rowNumberValues.values(), 1);
        ListUtils.ValuePosition<Double> lowestPosition = ListUtils.findTop(rowNumberValues.values(), -1);
        for (Map.Entry<Integer, Double> sheetRowNumValueEntry : rowNumberValues.entrySet()) {
            int rowIndex = sheetRowNumValueEntry.getKey();


            Double numValue = sheetRowNumValueEntry.getValue();
            Color color = Color.WHITE;
            if (numValue != null) {
                color = this.coloringArrayService.toColor(lowestColor, highestColor, lowestPosition.getValue(), highestPosition.getValue(), numValue);
                Row row = sheet.getRow(rowIndex);
                Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                XSSFCellStyle cellStyle = ExcelStyleUtils.newCellStyle((XSSFWorkbook) sheet.getWorkbook(), null, color);
//                cellStyle = ExcelStyleUtils.applyBackgroundColor(cellStyle, color);
                cell.setCellStyle(cellStyle);
            }
        }
    }

    private Map<Integer, Double> getCellNumValueMaps(Sheet sheet, int colIndex) {
        Map<Integer, Double> rowNumberValues = new LinkedHashMap<>();
        int rowIndex = 0;
        for (Row row : sheet) {
            Cell cell = row.getCell(colIndex);
            if (cell == null) continue;
            Double cellNumValue = null;
            if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                cellNumValue = cell.getNumericCellValue();
            } else if (cell.getCellTypeEnum() == CellType.STRING) {
                String cellStrValue = cell.getStringCellValue();
                cellNumValue = NumberUtils.toDoubleIfPossible(cellStrValue);
            }
            rowNumberValues.put(rowIndex, cellNumValue);
            rowIndex++;
        }
        return rowNumberValues;
    }
}
