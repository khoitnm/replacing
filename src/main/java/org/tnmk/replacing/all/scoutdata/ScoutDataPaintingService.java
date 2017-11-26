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
                new Color(61, 170, 81),
                new Color(210, 247, 218),
                "Value"
        );
        paintColumnsByValues(sheet,
                new Color(180, 207, 247),
                new Color(106, 115, 153),
                "Acceleration", "Pace", "Jumping", "Stamina", "Strength"
        );
        paintColumnsByValues(sheet,
                new Color(247, 220, 221),
                new Color(180, 52, 51),
                "Tackling", "Marking", "Positioning", "Head"
//                "Finishing", "Long shot", "Off The Ball",
//                "Dribbling", "Technique",
//                "Passing", "Creativity", "Crossing"
        );
        paintColumnsByValues(sheet,
                new Color(217, 216, 247),
                new Color(96, 67, 180),
//                "Tackling", "Marking", "Positioning", "Head",
                "Finishing", "Long shot", "Off The Ball"
//                "Dribbling", "Technique",
//                "Passing", "Creativity", "Crossing"
        );
        paintColumnsByValues(sheet,
                new Color(247, 240, 221),
                new Color(212, 161, 6),
//                "Tackling", "Marking", "Positioning", "Head",
//                "Finishing", "Long shot", "Off The Ball",
                "Dribbling", "Technique"
//                "Passing", "Creativity", "Crossing"
        );
        paintColumnsByValues(sheet,
                new Color(224, 247, 228),
                new Color(16, 212, 0),
//                "Tackling", "Marking", "Positioning", "Head",
//                "Finishing", "Long shot", "Off The Ball",
//                "Dribbling", "Technique"
                "Passing", "Creativity", "Crossing"
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