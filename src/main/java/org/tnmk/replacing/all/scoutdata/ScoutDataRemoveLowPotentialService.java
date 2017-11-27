package org.tnmk.replacing.all.scoutdata;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tnmk.replacing.all.util.NumberUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Service
public class ScoutDataRemoveLowPotentialService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ScoutDataRemoveLowPotentialService.class);
    public static final double MIN_POTENTIAL_RATE = 0.76;

    public void removeLowPotentialRate(Sheet sheet) {
        int colPotentialAvailabilityIndex = ScoutDataHelper.findColumnWithHeader(sheet, ScoutDataHelper.HEADER_POT_ABILITY);
        int colCurrentAvailabilityIndex = ScoutDataHelper.findColumnWithHeader(sheet, ScoutDataHelper.HEADER_CURRENT_ABILITY);
        int colCurrentRateIndex = ScoutDataHelper.findColumnWithHeader(sheet, ScoutDataHelper.HEADER_CURRENT_RATE);

        removeRow(sheet, row -> {
            double currentAvailability = row.getCell(colCurrentAvailabilityIndex).getNumericCellValue();
            double potentialAvailability = row.getCell(colPotentialAvailabilityIndex).getNumericCellValue();
            double improveAvailability = potentialAvailability - currentAvailability;
            String currentRateString = row.getCell(colCurrentRateIndex).getStringCellValue();
            double currentRate = NumberUtils.toDoubleIfPossible(currentRateString.substring(0, currentRateString.length() - 1)) / 100;
            double improveRate = improveAvailability * 2 / 10 / 100;
            double potentialRate = currentRate + improveRate;

            LOGGER.info("Row[{}]: potential rate: {}", row.getRowNum(), potentialRate);
            return potentialRate < MIN_POTENTIAL_RATE;
        });
    }

    public void removeRow(Sheet sheet, Function<Row, Boolean> removingRowFiler) {
        List<Integer> removingRowIndexes = new ArrayList<>();
        int i = 0;
        Iterator<Row> rowIterable = sheet.rowIterator();
        while (rowIterable.hasNext()) {
            Row row = rowIterable.next();
            if (i < ScoutDataHelper.ROW_DATA_CONTENT) {
                i++;
                continue;
            }
            if (removingRowFiler.apply(row) == true) {
                rowIterable.remove();
//                sheet.removeRow(row);
                LOGGER.info("Row[{}]: removed!!!", i);
            }
            i++;
        }
    }
}
