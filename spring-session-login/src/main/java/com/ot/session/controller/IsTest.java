package com.ot.session.controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class IsTest {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("d:/1.txt");
        byte[] data = new byte[1024];
        int read = fis.read(data);
        System.out.println(read);
        System.out.println(new String(data, 0, read));

        String str="世界";
    }
}
