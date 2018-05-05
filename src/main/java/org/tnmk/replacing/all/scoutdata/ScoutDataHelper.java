package org.tnmk.replacing.all.scoutdata;

import org.apache.poi.ss.usermodel.Sheet;
import org.tnmk.replacing.all.excel.ExcelSearchUtils;

public class ScoutDataHelper {
    public static final int ROW_DATA_HEADER = 1;
    public static final int ROW_DATA_CONTENT = 2;
    public static final int COL_NAME = 0;
    public static final String HEADER_POTENTIAL_RATE = "Potential Rate";
    public static final String HEADER_POT_ABILITY = "Pot Ability";
    public static final String HEADER_CURRENT_ABILITY = "Ability";
    public static final String HEADER_CURRENT_RATE = "Scout";

    public static int findColumnWithHeader(Sheet sheet, String header) {
        return ExcelSearchUtils.findFirstColumnIndexOnRow(sheet, ROW_DATA_HEADER, header);
    }
}
