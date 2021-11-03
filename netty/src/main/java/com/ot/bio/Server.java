package com.ot.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * socket的长短连接指的是 客户端和服务器在整个通讯过程当中使用同一个socket对象，短连接是指一次通讯结束之后就关闭socket，也就是每次都new
 * 一个socket
 */
public class Server {
    private static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务器启动");
        while (true) {
            Socket socket = serverSocket.accept();
            handler(socket);
        }
    }

    public static void handler(Socket socket) throws IOException {
        try {
            byte[] bytes = new byte[1024];
            InputStream is = socket.getInputStream();
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //处理完一个socket就关闭掉
//            socket.close();
        }
    }
}
