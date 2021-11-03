package com.ot.nio.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * mappedBuffer:可以让文件在内存当中修改，操作系统不需要拷贝,性能较高
 * 用户进程通过 mmap() 函数向内核(kernel)发起系统调用，上下文从用户态(user space)切换为内核态(kernel space)。
 * <p>
 * 将用户进程的内核空间的读缓冲区(read buffer)与用户空间的缓存区(user buffer)进行内存地址映射。
 * <p>
 * CPU 利用 DMA 控制器将数据从主存或硬盘拷贝到内核空间(kernel space)的读缓冲区(read buffer)。
 * <p>
 * 上下文从内核态(kernel space)切换回用户态(user space)，mmap 系统调用执行返回。
 * <p>
 * 用户进程通过 write() 函数向内核(kernel)发起系统调用，上下文从用户态(user space)切换为内核态(kernel space)。
 * <p>
 * CPU 将读缓冲区(read buffer)中的数据拷贝到网络缓冲区(socket buffer)。
 * <p>
 * CPU 利用 DMA 控制器将数据从网络缓冲区(socket buffer)拷贝到网卡进行数据传输。
 * <p>
 * 上下文从内核态(kernel space)切换回用户态(user space)，write 系统调用执行返回。
 * <p>
 * <p>
 * <p>
 * 使用内存映射，仅需要硬盘到用户缓存区这一次拷贝过程 1次cpu拷贝，4次上下文切换和2次DMA拷贝
 */
public class MappedBuffer {

    public static void main(String[] args) throws Exception {
        /**
         * **"r" : ** 以只读方式打开。调用结果对象的任何 write 方法都将导致抛出 IOException。
         * "rw": 打开以便读取和写入。
         * "rws": 打开以便读取和写入。相对于 "rw"，"rws" 还要求对“文件的内容”或“元数据”的每个更新都同步写入到基础存储设备。
         * "rwd" : 打开以便读取和写入，相对于 "rw"，"rwd" 还要求对“文件的内容”的每个更新都同步写入到基础存储设备。
         *
         */
        RandomAccessFile rw = new RandomAccessFile("d:/1.txt", "rw");
        FileChannel channel = rw.getChannel();
//        channel.transferTo()；
        /**
         * 最终返回的是 DirectByteBuffer
         * 1.使用读写模式
         * 2.可以直接修改的位置
         * 3.可以映射到内存的大小。文件的多少个字节可以操作
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(9, (byte) 'e');
        rw.close();
    }
}
