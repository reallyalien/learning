package com.ot.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class FileDemo {

    public static void main(String[] args) throws Exception{
        String src = "E:\\develop\\IDEA\\ideaIU-2019.2.4.exe";
        String desc = "D:\\1111.exe";
        FileChannel fis = new FileInputStream(src).getChannel();
        FileChannel fos = new FileOutputStream(desc).getChannel();

        //文件拷贝,底层零拷贝
//        fos.transferFrom(fis,0,fis.size());
//        long start = System.currentTimeMillis();
        fis.transferTo(0,fis.size(),fos);
//        long end = System.currentTimeMillis();
//        System.out.println(end-start);
        fis.close();
        fos.close();

    }

}
