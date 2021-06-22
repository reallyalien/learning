package com.ot.bio;

import java.io.FileInputStream;
import java.io.IOException;

public class Io {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("E:\\备份\\02_CentOS-7-x86_64-DVD-1708.iso");
        int available = fis.available();//读取iso文件大概2G左右
        System.out.println();
    }
}
