package com.ot.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 客户端
 */
public class SocketClient1 {

    public static void main(String[] args) throws IOException {
        //客户端启动必备
        Socket client = null;
        //实例化客户端与服务端通信的流
        ObjectInputStream input = null;
        ObjectOutputStream output = null;
        //服务器端的通信地址
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9999);
        try {
            client = new Socket();
            //连接服务器
            client.connect(address);
            output = new ObjectOutputStream(client.getOutputStream());//先获得输入流会阻塞
            input = new ObjectInputStream(client.getInputStream());
            //发出请求
            output.writeUTF("hello,我是client1");//写数据到缓存区
            output.flush();//清空缓存区
            //接收服务端的输出
            System.out.println(input.readUTF());//被阻塞
            System.out.println("客户端结束");
        } catch (Exception e) {
            if (input != null) input.close();
            if (output != null) output.close();
            if (client != null) client.close();
        }
    }
}
