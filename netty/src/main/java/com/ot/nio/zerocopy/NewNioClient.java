package com.ot.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * https://blog.csdn.net/weixin_36180611/article/details/113325134
 */
public class NewNioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 7777));
        String fileName = "E:\\Java\\视频学习资料\\Kafka\\1.大纲入门(Av752590553,P1).mp4";
        InputStream inputStream = new FileInputStream(fileName);
        FileChannel fileChannel = ((FileInputStream) inputStream).getChannel();

        //准备发送
        long start = System.currentTimeMillis();
        //在linux下 transformTo可用发送完成
        //window下 transformTo一次只能传输8m，大文件得分段
        //底层基于sendfile
        long l = fileChannel.transferTo(0, fileChannel.size(), socketChannel);//底层使用零拷贝
        long end = System.currentTimeMillis();
        System.out.println("发送的总的字节数=" + l + "耗时=" + (end - start));
    }
}
