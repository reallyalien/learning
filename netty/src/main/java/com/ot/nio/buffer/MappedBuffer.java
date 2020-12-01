package com.ot.nio.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * mappedBuffer:可以让文件在内存当中修改，操作系统不需要拷贝,性能较高
 */
public class MappedBuffer {

    public static void main(String[] args) throws Exception {
        RandomAccessFile rw = new RandomAccessFile("d:/1.txt", "rw");
        FileChannel channel = rw.getChannel();
        /**
         * DirectByteBuffer
         * 1.使用读写模式
         * 2.可以直接修改的位置
         * 3.可以映射到内存的大小。文件的多少个字节可以操作
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');
        rw.close();
    }
}
