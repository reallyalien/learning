package com.ot.nio.time;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandler implements Runnable {

    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    public TimeClientHandler(String host, int port) {
        try {
            this.host = host;
            this.port = port;
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            doConnection();
        } catch (IOException e) {

        }
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey selectionKey = null;
                while (iterator.hasNext()) {
                    selectionKey = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(selectionKey);
                    } catch (Exception e) {
                        if (selectionKey != null) {
                            selectionKey.cancel();
                        }
                        if (selectionKey.channel() != null) {
                            selectionKey.channel().close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void doConnection() throws IOException {
        //如果连接成功，则直接注册到多路复用器上，发送请求消息，读应答
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            //连接不成功，则等待连接事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void handleInput(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isValid()) {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            if (selectionKey.isConnectable()) {
                if (socketChannel.finishConnect()) {
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    doWrite(socketChannel);
                } else {
                    System.exit(1);//连接失败，进程退出
                }
            }
            if (selectionKey.isReadable()) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                //由于设置异步非阻塞，这里的read立即返回
                int readBytes = socketChannel.read(buffer);
                if (readBytes > 0) {
                    //读取到字节，进行编解码操作
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String body = new String(bytes, "utf-8");
                    System.out.println("the time server receive body :" + body);
                    this.stop = true;
                } else if (readBytes < 0) {
                    selectionKey.cancel();
                    socketChannel.close();
                } else {
                    //读到0个字节
                }
            }
        }
    }

    private void doWrite(SocketChannel socketChannel) throws IOException {
        byte[] bytes = "query time order".getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        socketChannel.write(buffer);
        if (!buffer.hasRemaining()) {
            System.out.println("send order 2 server succeed");
        }
    }
}
