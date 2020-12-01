package com.ot.bio;

import sun.nio.ch.FileChannelImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.*;

/**
 * 服务端
 */
public class SocketServerPool {

    /**
     * 实际机器的cpu核心数
     */
    private static final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws IOException {
        //服务端开启
        ServerSocket server = new ServerSocket();
        //监听端口
        server.bind(new InetSocketAddress(9999));
        System.out.println("server start......");
        while (true) {
            final Socket socket = server.accept();
            System.out.println("客端端："+socket.getRemoteSocketAddress()+"进来了");
            pool.execute(new ServerTask(socket));
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
                System.out.println("accept clinet message:" + msg);
                outputStream.writeUTF("Hello,我是server");
                Thread.sleep(20000);
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

