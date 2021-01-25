package com.ot.nio.chat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 服务端只有一个线程
 */

public class Server {
    //定义属性
    private Selector selector;
    private ServerSocketChannel listenerChannel;
    private static final int port = 6667;

    //完成初始化工作
    public Server() {
        try {
            selector = Selector.open();
            listenerChannel = ServerSocketChannel.open();
            listenerChannel.socket().bind(new InetSocketAddress(port));
            listenerChannel.configureBlocking(false);
            //通过key.attachment()获取这个值
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT,"aaaaa");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听
    public void listener() {
        System.out.println("listener开始：" + Thread.currentThread());
        System.out.println("服务端监听消息");
        try {
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    System.out.println("开始处理：" + Thread.currentThread());
                    //有事件处理
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.isAcceptable()) {
                            Object attachment = key.attachment();
                            System.out.println(attachment);
                            System.out.println("接收accepe：" + Thread.currentThread());
                            //建立与客户端的连接
                            //下面的2个写法其实是一样的，因为只有listenerChannel才可以接受accept,而socketChannel只能读写请求
//                            System.out.println(key.channel().hashCode());
//                            System.out.println(listenerChannel.hashCode());
                            SocketChannel socketChannel = listenerChannel.accept();
                            //注册
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "上线");
                        }
                        if (key.isReadable()) {
                            System.out.println("read：" + Thread.currentThread());
                            //读取数据
                            read(key);
                        }
                        //删除当前key,防止重复处理
                        keyIterator.remove();
                    }
                } else {
                    System.out.println("等待中");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    //读取客户端消息
    public void read(SelectionKey key) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) key.channel();
            //创建缓存区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buffer);
            if (count > 0) {
                //读取到数据
                buffer.flip();
                String msg = new String(buffer.array(), 0, count);
                //输出消息
                System.out.println("from client:" + msg);
                //向其他客户端转发消息
                sendInfoToOther(msg, socketChannel);
            }
        } catch (Exception e) {
            try {
                System.out.println(socketChannel.getRemoteAddress().toString().substring(1) + "\t" + "离线");
                //取消注册
                key.cancel();
                //关闭通道
                socketChannel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {

        }
    }

    //转发消息给其他客户端
    public void sendInfoToOther(String msg, SocketChannel self) {
        System.out.println("服务器转发消息中.....");
        //遍历所有注册到select上的socketChannel，并排出自己
        Set<SelectionKey> keys = selector.keys();
        keys.forEach(key -> {
            Channel target = key.channel();//这里获取的channel，除了socketChannel(客户端)还有serverSocketChannel(服务端)
            //直接强转socketChannel会报错，故才有了下面判断类型这一说
//            //排除自己
            if (target instanceof SocketChannel && target != self) {
                SocketChannel desc = (SocketChannel) target;
                //转发消息
                try {
                    desc.write(ByteBuffer.wrap(msg.getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.listener();
    }
}
