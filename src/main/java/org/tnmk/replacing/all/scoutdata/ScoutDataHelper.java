package org.tnmk.replacing.all.scoutdata;

import org.apache.poi.ss.usermodel.Sheet;
import org.tnmk.replacing.all.excel.ExcelSearchUtils;

public class ScoutDataHelper {
    public static final int ROW_DATA_HEADER = 1;

    public static int findColumnWithHeader(Sheet sheet, String header) {
        return ExcelSearchUtils.findFirstColumnIndexOnRow(sheet, ROW_DATA_HEADER, header);
    }
}
