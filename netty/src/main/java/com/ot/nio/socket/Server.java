package com.ot.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 因为是一个线程通过选择器来操作通道，那么选择器在操作通道时，必定在处理一个通道的时候，
 * 另一个事件已就绪的通道处于等待状态。在确定一个通道事件就绪之后，才能去操作这个通道。上
 * 文中讲到使用注册方法register使用的代码示例，将ServerSocketChannel对象向选择器注册，同
 * 时关注了这个通道的OP_ACCEPT操作类型事件，那么我们什么时候能确定该通道的accept事件就绪
 * ，可以操作这个通道了。选择器为我们提供了三个重载的 select() 方法，这三个方法的主要功
 * 能就是选择是否阻塞直到该选择器中的通道所关注的事件就绪，来让程序继续往下运行。
 */
public class Server {

    public static void main(String[] args) throws IOException {
        //1.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2.切换非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //3.绑定端口
        serverSocketChannel.bind(new InetSocketAddress(9998));
        //4.获取选择器
        Selector selector = Selector.open();
        //5.将通道注册到选择器上，并且指定监听接收事件这个类型 16
        //a.每个channel向selector注册时都会创建一个selectionKey
        //b.选择键将channel与selector建立连接并维护channel时事件
        //c.可以通过cancel取消键取消的键不会立即从selector中移除,
        // 而是添加到cancelledKeys中,在下一次select操作时移除它.所以在调用某个key时,需要使用isValid进行校验.
        //我们每为一个通道执行注册方法，都会返回一个selectKey，那么这个选择器上所有的selectkey通过selectkeys来获取
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端已准备...");
        //6.轮询式的获取选择器上已经准备就绪的事件
        //使用select()来监听，它会一直阻塞，直到至少一个事件到达,超时不等待
        while (true) {
            if (selector.select(2000) == 0) {
                System.out.println("等待了2s没有监听到事件，去做其他事情");
                int 啊=10;
            }
            System.out.println("正在等待事件就绪");
            if (selector.select(2000) > 0) {
                System.out.println("监听到事件，去处理");
                //7.获取当前选择器中所有注册的选择键(已就绪的监听事件)
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    //8.获取就绪的事件
                    SelectionKey next = it.next();
                    //9.判断具体是什么事情准备就绪
                    if (next.isAcceptable()) {
                        //10.若接收就绪，获取客户端连接,这里不会阻塞
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        //11.切换非阻塞模式
                        socketChannel.configureBlocking(false);
                        //12.将该通道注册到选择器上
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (next.isReadable()) {
                        //13.获取当前选择器上读就绪状态的通道
                        SocketChannel channel = (SocketChannel) next.channel();
                        //14.读取数据
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int len = 0;
                        while ((len = channel.read(buffer)) > 0) {
                            //切换模式写数据,这里输出到控制台
                            buffer.flip();
                            System.out.println(new String(buffer.array(), 0, len));
                            buffer.clear();
                        }
                    }
                    //15.取消选择键，
                    //每一个事件关键字被处理完之后都必须移除，否则下一次轮询时，这个事件会被重复处理
                    it.remove();
                    //close方法，先唤醒被阻塞的线程，置空所有的通道，所有就绪的slectkey，让这个选择器的轮询组件也停下来
//                    selector.close();
                }
            }

        }
//        System.out.println("方法结束");
    }
}
