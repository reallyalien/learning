package com.ot.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileCopyDemo {


    public static void main(String[] args) throws IOException {
        String src = "E:\\Java\\视频学习资料\\Kafka\\1.大纲入门(Av752590553,P1).mp4";
        String desc = "D:\\1.mp4";
        FileCopyDemo.fastCopy(src, desc);
    }

    public static void fastCopy(String src, String desc) throws IOException {
        FileInputStream fis = new FileInputStream(src);
        FileChannel fisChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream(desc);
        FileChannel fosChannel = fos.getChannel();
        /**
         * allocateDirect是操作系统级别的 省略复制操作，效率提高，但是系统内存分配比jvm分配内存更耗时
         * allocate是jvm级别的，数据量大时这个快
         * 当java应用程序需要读取外部资源时，需要先读取到系统内存当中，然后再复制给jvm内存
         */
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 8);
        /**
         * position limit capacity
         */
        while (true) {
            //读取数据到buffer当中
            int i = fisChannel.read(buffer);
            if (i == -1) {
                //读取结束
                break;
            }
            /**
             * buffer有2种形式，切换模式,更改position为0，limit为position
             */
            buffer.flip();
            //切换成读模式之后，再将缓存区的文件写入到输出
            fosChannel.write(buffer);
            //清空缓存区,重置标志位,这里如果不清空，则读到最后，position=limit，
            //每次读取到的都是0，永远也不会结束循环
            buffer.clear();
        }
        fis.close();
        fos.close();
    }
}
