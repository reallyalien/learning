package com.ot.nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewNioServer {

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(7777);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(address);
        ByteBuffer buffer=ByteBuffer.allocate(4096);
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            int len=0;
            while (-1 !=len){
                len=socketChannel.read(buffer);
            }
            buffer.clear();
        }
    }
}
