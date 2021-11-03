package com.ot.nio.buffer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

public class RandomAccessFileTest {

    public static void main(String[] args) {
        multiThreadWrite();
    }

    /**
     * 多线程写
     */
    public static void multiThreadWrite() {
        try {
            RandomAccessFile file = new RandomAccessFile(new File("d:/2.txt"), "rw");
            file.setLength(1024 * 1024);
            file.close();
            // 所要写入的文件内容
            String s1 = "hello1";
            String s2 = "hello2";
            String s3 = "hello3";
            String s4 = "hello4";
            String s5 = "hello5";
            // 利用多线程同时写入一个文件
            new FileWriteThread(8 * 0, s1.getBytes(Charset.defaultCharset())).start(); // 从文件的1024字节之后开始写入数据
            new FileWriteThread(8 * 1, s2.getBytes(Charset.defaultCharset())).start(); // 从文件的2048字节之后开始写入数据
            new FileWriteThread(8 * 2, s3.getBytes(Charset.defaultCharset())).start(); // 从文件的3072字节之后开始写入数据
            new FileWriteThread(8 * 3, s4.getBytes(Charset.defaultCharset())).start(); // 从文件的4096字节之后开始写入数据
            new FileWriteThread(8 * 4, s5.getBytes(Charset.defaultCharset())).start(); // 从文件的5120字节之后开始写入数据
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 利用线程在文件的指定位置写入指定数据
    static class FileWriteThread extends Thread {
        private int skip;
        private byte[] content;

        public FileWriteThread(int skip, byte[] content) {
            this.skip = skip;
            this.content = content;
        }

        public void run() {
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile("D:/2.txt", "rw");
                raf.seek(skip);
                raf.write(content);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    raf.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
