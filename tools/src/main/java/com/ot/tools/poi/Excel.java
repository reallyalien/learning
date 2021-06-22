package com.ot.tools.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.UUID;

public class Excel {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        excel2007AboveOperateOld();
    }

    public static void excel2007AboveOperateOld() throws IOException, InvalidFormatException {
        String path = "D:/test/out.xlsx";
        XSSFWorkbook workbook1 = new XSSFWorkbook(new File(path));
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1, 100);
        Sheet first = sxssfWorkbook.getSheetAt(0);
        for (int i = 0; i < 100000; i++) {
            Row row = first.createRow(i);
            for (int j = 0; j < 11; j++) {
                if (i == 0) {
                    // 首行
                    if (j == 0) {
                        row.createCell(j).setCellValue(String.valueOf(j));
                    } else
                        row.createCell(j).setCellValue(String.valueOf(UUID.randomUUID().toString()));
                }
            }
        }
        FileOutputStream out = new FileOutputStream("d:/test/workbook.xlsx");
        sxssfWorkbook.write(out);
        out.close();
    }
}
