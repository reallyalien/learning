package com.ot.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动");
        while (true) {
            System.out.println("主线程：" + Thread.currentThread().getName());
            System.out.println("服务器等待连接");
            Socket socket = serverSocket.accept();
            pool.execute(() -> {
                try {
                    handler(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void handler(Socket socket) throws IOException {
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            String currentThreadName = Thread.currentThread().getName();
            System.out.println("连接客户端线程：" + currentThreadName);
            while (true) {
                System.out.println("读取数据线程：" + currentThreadName);
                System.out.println("等待read:" + currentThreadName);
                int len = inputStream.read(bytes);//没数据阻塞
                if (len != -1) {
                    System.out.println(new String(bytes, 0, len));
                } else {
                    System.out.println("读取结束:" + currentThreadName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
}
