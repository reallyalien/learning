package com.ot.jvm.day05.object.item6;

import java.io.IOException;
import java.net.Socket;

/**
 * @Title: SocketOperator
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/21
 * @Version: 1.0
 */
public class SocketOperator {

    private final Socket socket;

    public SocketOperator(String host, Integer port) throws IOException {
        this.socket = new Socket(host, port);
    }

    public String readAsString() {
        // 读数据逻辑
        return null;
    }

    public String writeAsString(String msg) {
        // 写数据逻辑
        return null;
    }

    @Override
    protected void finalize() throws Throwable {
        // 关闭网络连接
        System.out.println("Release connection");
        socket.close();
    }

    public void close() throws IOException {
        socket.close();
    }
}