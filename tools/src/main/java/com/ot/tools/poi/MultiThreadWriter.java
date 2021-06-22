package com.ot.tools.poi;

import java.io.File;

public class MultiThreadWriter {


    private final static String path = "d:/test/test.xlsx";

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        writer();
    }

    public static synchronized void writer() {
        File file = new File(path);
        System.out.println(file.exists());
    }
}
