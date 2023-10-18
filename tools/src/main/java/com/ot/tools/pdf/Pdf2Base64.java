package com.ot.tools.pdf;

import org.springframework.util.Base64Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
        String data1 = Base64Utils.encodeToString(data);
        System.out.println(data1);
        return data1;
    }


    public static void main(String[] args) {
        fileToBase64();
    }
}
