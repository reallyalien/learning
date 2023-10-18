package com.ot.pdf;

import org.springframework.util.Base64Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * @Title: Pdf2Base64
 * @Author wangtao
 * @Date 2023/9/15 15:20
 * @description:
 */
public class Pdf2Base64 {


    public static String fileToBase64() {
        String imgFilePath = "e:/20230627170126296printreport.pdf";
//        String[] res = imgFilePath.split("\\.");
//        String pos = res[res.length - 1];
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String data1 = Base64Utils.encodeToString(data);
        String data1 = Base64.getEncoder().encodeToString(data);
        System.out.println(data1);
        return data1;
    }


    public static void main(String[] args) {
//        fileToBase64();
        long i = 24;
        System.out.println(i * 24 * 60 * 60 * 1000);


        StringBuilder sb = new StringBuilder();

        sb.append(" ").append("_");
        sb.append(" ").append("_");
        sb.append(" ").append("_");
        String string = sb.toString();
        String[] split = string.split("_");
        System.out.println();


        String hello = "hello";
        //-4, -1
        String s = hello.substring(1,4);
        System.out.println(s);
    }
}
