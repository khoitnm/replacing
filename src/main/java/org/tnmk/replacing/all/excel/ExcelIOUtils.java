package org.tnmk.replacing.all.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.tnmk.replacing.all.exception.UnexpectedException;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public final class ExcelIOUtils {
    private ExcelIOUtils() {
        //Utilities
    }

    public static XSSFWorkbook readCsvAsXlsx(String csvAbsFileName, String deliminator) {
        try {
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("sheet1");
            String currentLine = null;
            int RowNum = 0;
            BufferedReader br = new BufferedReader(new FileReader(csvAbsFileName));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(deliminator);
                RowNum++;
                XSSFRow currentRow = sheet.createRow(RowNum);
                for (int i = 0; i < str.length; i++) {
                    currentRow.createCell(i).setCellValue(str[i]);
                }
            }
            return workBook;
        } catch (Exception ex) {
            throw new UnexpectedException("Cannot convert csv to excel: " + ex.getMessage(), ex);
        }
    }

    /**
     * * @param csvFileAddress
     *
     * @param xlsxAbsFileName
     * @param deliminator     usually a comma character.
     * @return
     */
    public static XSSFWorkbook csvToXlsx(String csvAbsFileName, String deliminator, String xlsxAbsFileName) {
        XSSFWorkbook workBook = readCsvAsXlsx(csvAbsFileName, deliminator);
        writeToFile(workBook, xlsxAbsFileName);
        return workBook;
    }

    public static void writeToFile(Workbook workBook, String absFileName) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(absFileName);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (Exception ex) {
            throw new UnexpectedException("Cannot write csv to excel: " + ex.getMessage(), ex);
        }
    }
}
