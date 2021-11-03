package com.ot.nio.trans;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;

public class TransDemo {

    /**
     * 273M文件，测试不同api传输速度
     */
    private static final String source = "d:/test/1.exe";
    private static final String target = "d:/test/2.exe";

    /**
     * 每读取一次字节都需要跟操作系统做交互，
     *
     * @throws IOException
     */
    @Test
    public void testFileInputStream() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(source);
        FileOutputStream fileOutputStream = new FileOutputStream(target);
        byte[] bytes = new byte[1024];//1kb读取
        int len = 0;
        long start = System.currentTimeMillis();
        while ((len = fileInputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, len);
        }
        fileInputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));//2172
    }

    @Test
    public void testBufferInputStream() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(source);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(target);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        byte[] bytes = new byte[1024];
        int len = 0;
        long start = System.currentTimeMillis();
        while ((len = bufferedInputStream.read(bytes)) != -1) {

            bufferedOutputStream.write(bytes, 0, len);
        }
        fileInputStream.close();
        bufferedInputStream.close();
        bufferedOutputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));//433
    }

    @Test
    public void testFileChannel() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(source);
        FileOutputStream fileOutputStream = new FileOutputStream(target);
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        long start = System.currentTimeMillis();
        while (fileInputStreamChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            fileOutputStreamChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        fileInputStreamChannel.close();
        fileOutputStreamChannel.close();
        fileInputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));//1396
    }

    @Test
    public void testFileChannelTrans() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(source);
        FileOutputStream fileOutputStream = new FileOutputStream(target);
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();
        long start = System.currentTimeMillis();
        long l = fileInputStreamChannel.transferTo(0, fileInputStreamChannel.size(), fileOutputStreamChannel);
        fileInputStreamChannel.close();
        fileOutputStreamChannel.close();
        fileInputStream.close();
        fileOutputStream.flush();
        fileOutputStream.close();
        long end = System.currentTimeMillis();
        System.out.println("发送的文件大小：" + (l / 1024 / 1024) + "MB,耗时：" + (end - start));//170
    }

    @Test
    public void testMapper() throws IOException {
        RandomAccessFile randomAccessFile1 = new RandomAccessFile(source, "rw");
        RandomAccessFile randomAccessFile2 = new RandomAccessFile(target, "rw");
        FileChannel inChannel = randomAccessFile1.getChannel();
        FileChannel outChannel = randomAccessFile2.getChannel();
        long start = System.currentTimeMillis();
        MappedByteBuffer mappedByteBuffer = inChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
        outChannel.write(mappedByteBuffer);
        long end = System.currentTimeMillis();
        System.out.println("发送的文件大小：" + (inChannel.size() / 1024 / 1024) + "MB,耗时：" + (end - start));//381
    }
}
