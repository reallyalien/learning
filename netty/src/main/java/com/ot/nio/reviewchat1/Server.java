package com.ot.nio.reviewchat1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private int port = 9999;


    public Server() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listener() {
        while (true) {
            try {
                int count = selector.select(2000);
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "上线了");
                        }
                        if (selectionKey.isReadable()) {
                            read(selectionKey);
                        }
                        iterator.remove();
                    }
                } else {
//                    System.out.println("等待中");
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    selector.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public void read(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(buffer);
            if (read > 0) {
                System.out.println(buffer);
                String msg = new String(buffer.array(), 0, buffer.position());
                System.out.println(Thread.currentThread());
                System.out.println("接收到：" + socketChannel.getRemoteAddress() + "的消息：" + msg);
                sendTo(socketChannel, msg);
            }
        } catch (Exception e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + "离线");
                selectionKey.cancel();
                socketChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void sendTo(SocketChannel currentSocketChannel, String msg) {
        Set<SelectionKey> allKeys = selector.keys();
        allKeys.forEach((selectionKey -> {
            SelectableChannel channel = selectionKey.channel();
            if (channel instanceof SocketChannel) {
                SocketChannel socketChannel = (SocketChannel) channel;
                if (socketChannel != currentSocketChannel) {
                    try {
                        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(Charset.defaultCharset()));
                        socketChannel.write(buffer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.listener();
    }
}
