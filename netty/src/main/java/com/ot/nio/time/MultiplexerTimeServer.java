package com.ot.nio.time;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("the time server is start....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                int i = selector.select(1000);
                if (i > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        try {
                            handlelnput(selectionKey);
                        } catch (Exception e) {
                            if (selectionKey != null) {
                                selectionKey.cancel();
                                if (selectionKey.channel() != null) {
                                    selectionKey.channel().close();
                                }
                            }
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

    public void handlelnput(SelectionKey selectionKey) {
        try {
            if (selectionKey.isValid()) {
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置异步非阻塞
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_ACCEPT);
                }
                if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
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
                        String currentTime = "query time order ".equalsIgnoreCase(body)
                                ? new Date(System.currentTimeMillis()).toString() : "bad order";
                        doWrite(socketChannel,currentTime);
                    } else if (readBytes < 0) {//链路已经关闭
                        selectionKey.cancel();
                        socketChannel.close();
                    } else {
                        //
                    }
                }
            }

        } catch (IOException e) {

        }

    }

    private void doWrite(SocketChannel socketChannel, String currentTime) {
        byte[] bytes = currentTime.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        try {
            //socketChannel是异步非阻塞的，并不保证一次可以将需要发送的字节都发送出去，此时会出现写半包的情况
            //我们需要注册写操作，不断轮询selector将没有发送完的byteBuffer发送完毕，可以用过byteBuffer的hasRemain（）
            //方法判断消息是否发送完毕
            socketChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
