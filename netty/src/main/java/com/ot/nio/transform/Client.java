package com.ot.nio.transform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            // 模拟三个发端
            new Thread() {
                public void run() {
                    try {
                        //	1、获取连接
                        SocketChannel socketChannel = SocketChannel.open();
                        socketChannel.socket().connect(new InetSocketAddress("127.0.0.1", 8888));

                        //	切换堵塞模式  默认堵塞
                        //    socketChannel.configureBlocking(false);

                        File file = new File("D:\\" + "1.txt");
                        FileChannel fileChannel = new FileInputStream(file).getChannel();
                        //	buffer空间的申请      分配缓冲区的大小
                        ByteBuffer buffer = ByteBuffer.allocate(100);
                        int num = 0;
                        //	发送数据给服务端
                        while ((num = fileChannel.read(buffer)) > 0) {
                            buffer.flip();
                            socketChannel.write(buffer);
                            buffer.clear();
                        }
                        //	关闭通道
                        if (num == -1) {
                            fileChannel.close();
                            socketChannel.shutdownOutput();
                        }

                        // 接受服务器
                        socketChannel.read(buffer);
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, buffer.limit(), Charset.forName("utf-8")));
                        buffer.clear();

                        //	关闭连接
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                ;
            }.start();
        }
        Thread.yield();
    }
}

