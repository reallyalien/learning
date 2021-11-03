package com.ot.nio.reviewchat1;

import com.ot.nio.chat.ClientNoSelect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {

    //定义相关属性
    private static final String host = "127.0.0.1";
    private static final int port = 9999;
    private SocketChannel socketChannel;
    private String username;

    //完成初始化工作
    public Client() {
        try {
            this.socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            this.socketChannel.configureBlocking(false);
            this.username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + "\t" + "准备好了");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    //向服务器发送消息
    public void sendMsg(String msg) {
        try {
            msg = username + "说" + msg;
            ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
            System.out.println(buffer);
            socketChannel.write(buffer);
            System.out.println("发送数据：" + msg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    //读取从服务器端的消息
    public void read() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            int read = socketChannel.read(buffer);
            if (read >0){
                System.out.println(new String(buffer.array(),0,buffer.position()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //启动客户端
        Client client = new Client();
        //启动一个线程,每个3s去读取数据
        new Thread(() -> {
            while (true) {
                client.read();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String msg = scanner.next();
            client.sendMsg(msg);
        }
    }
}
