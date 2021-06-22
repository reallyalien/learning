package com.ot.pot.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class ExcelUtils {

    public static void main(String[] args) throws Exception{
        excel2007AboveOperateOld();
    }

    public static void multiThread() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    excel2007AboveOperateOld();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        latch.await();
    }

    public synchronized static void excel2007AboveOperateOld() throws IOException, InvalidFormatException {
        String path = "D:/test/out.xlsx";
        String path1 = "D:/test/out1.xlsx";
        long start = System.currentTimeMillis();
        XSSFWorkbook workbook1 = new XSSFWorkbook(new File(path));
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1, 100);
        Sheet first = sxssfWorkbook.getSheetAt(0);
        for (int i = 0; i < 1000000; i++) {
            Row row = first.createRow(i);
            for (int j = 0; j < 11; j++) {
                row.createCell(j).setCellValue(String.valueOf(UUID.randomUUID().toString()));
            }
        }
        FileOutputStream out = new FileOutputStream(path1,true);
        sxssfWorkbook.write(out);
        out.close();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) / 1000);
    }
}
