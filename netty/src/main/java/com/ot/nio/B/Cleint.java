package com.ot.nio.B;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class Cleint {

    public static void main(String[] args) throws IOException {
        FileChannel fileChannel = new FileInputStream(new File("D:/1.txt")).getChannel();
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        int read = fileChannel.read(buffer);
        buffer.flip();
        System.out.println(new String(buffer.array()));
        SocketChannel socketChannel=SocketChannel.open(new InetSocketAddress("127.0.0.1",9998));
        socketChannel.write(buffer);
        buffer.clear();
    }
}
