package com.ot.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public class AioClient {

    private AsynchronousSocketChannel socketChannel;

    public AioClient(String host,int port) {
        init(host,port);
    }

    private void init(String host, int port) {
        InetSocketAddress ip = new InetSocketAddress(host, port);
        try {
            socketChannel = AsynchronousSocketChannel.open();
            socketChannel.connect(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(String msg) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            buffer.put(msg.getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            socketChannel.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void read() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //read方法是异步的，由操作系统实现
        Future<Integer> future = socketChannel.read(buffer);
        try {
            //get是阻塞方法，会等待操作系统处理结束之后才返回
            future.get();
            buffer.flip();
            System.out.println("from server :" + new String(buffer.array(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        if (socketChannel != null) {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socketChannel = null;
    }

    public static void main(String[] args) {
        AioClient aioClient = new AioClient("127.0.0.1",9999);
        System.out.println("enter msg send to server");
        Scanner scanner = new Scanner(System.in);
        for (;;){
            String s = scanner.nextLine();
            aioClient.write(s);
            aioClient.read();
        }
    }
}
