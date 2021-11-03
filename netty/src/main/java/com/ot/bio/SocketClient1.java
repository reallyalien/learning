package com.ot.bio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * 客户端
 */
public class SocketClient1 {

    public static void main(String[] args) throws IOException {
        //客户端启动必备
        Socket client = null;
        //实例化客户端与服务端通信的流
        InputStream is = null;
        OutputStream os = null;
        //服务器端的通信地址
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9999);
        try {
            client = new Socket();
            //连接服务器
            client.connect(address);
            os = client.getOutputStream();//先获得输入流会阻塞
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String s = scanner.nextLine();
                os.write(s.getBytes(Charset.defaultCharset()));
                os.flush();
            }
        } catch (Exception e) {
        } finally {
            client.close();
        }
    }
}
