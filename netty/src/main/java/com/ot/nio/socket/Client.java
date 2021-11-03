package com.ot.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Stream;

public class Client {

    public static void main(String[] args) throws IOException {
        new Thread(() -> {
            try {
                //1.获取通道,基于tcp连接
                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.connect(new InetSocketAddress("127.0.0.1", 9998));
                //2.切换非阻塞模式
                socketChannel.configureBlocking(false);
                //3.分配缓冲区
                ByteBuffer buffer = ByteBuffer.allocate(20);
                //4.发送数据给服务端
                Scanner scanner = new Scanner(System.in);
                System.out.println("客户端准备发送数据:");
                while (scanner.hasNext()) {
                    String next = scanner.next();
                    buffer.put(next.getBytes());
                    System.out.println(buffer);
                    buffer.flip();
                    socketChannel.write(buffer);
                    buffer.clear();
                }
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }).start();

    }
}
