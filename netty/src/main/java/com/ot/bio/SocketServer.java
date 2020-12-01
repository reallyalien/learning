package com.ot.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * 服务端
 */
public class SocketServer {

    public static void main(String[] args) throws IOException {
        //服务端开启
        ServerSocket server = new ServerSocket();
        //监听端口
        server.bind(new InetSocketAddress(9999));
        System.out.println("server start......");
        while (true) {
            Socket client = server.accept();
            new Thread(new ServerTask(client)).start();//这里会去创建新的线程去执行，可是accept只能一个一个接收
        }
    }

    private static class ServerTask implements Runnable {
        private Socket socket = null;

        public ServerTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() { /*拿和客户端通讯的输入输出流*/
            try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) { /*服务器的输入*/
                String msg = inputStream.readUTF();
                TimeUnit.SECONDS.sleep(5);
                System.out.println("accept clinet message:" + msg);
                outputStream.writeUTF("Hello,我是server");
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

