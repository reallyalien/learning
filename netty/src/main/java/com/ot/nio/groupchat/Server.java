package com.ot.nio.groupchat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * nio是同步非阻塞io，同步非阻塞，同步体现在 selector 仍然要去轮循判断 channel 是否准备好，
 * 非阻塞体现在这个过程中处理线程不会一直在等待，可以去做其他的事情。
 * 其实通知你是告诉你内核准备好了，你取数据的时候还是需要将内核中的数据拷贝到用户空间中。这段时间进程是会阻塞的
 * 1 ) 异步非阻塞例子:(网上看到的比较短小精悍的好例子,直接拿过来了)
 *
 * 老张爱喝茶，废话不说，煮开水。
 *
 * 出场人物：老张，水壶两把（普通水壶，简称水壶；会响的水壶，简称响水壶）。
 *
 * 1 老张把水壶放到火上，原地不动等水开。（同步阻塞）
 *
 *  ---------->老张觉得自己有点傻
 *
 * 2 老张把水壶放到火上，去客厅看毛骗，时不时去看看水开没有。（同步非阻塞）
 *
 *  ---------->老张觉得自己有点傻
 *
 * 于是变高端了，买了把会响笛的那种水壶。水开之后，能大声发出嘀~~~~的响声。
 *
 * 3 老张把响水壶放到火上，立等水开。（异步阻塞）
 *
 *  --------->老张觉得自己有点傻
 *
 * 4 老张把响水壶放到火上，去客厅看毛骗，水壶响之前不再去看它，响了再去拿壶。（异步非阻塞）
 *
 * ---------->嗯,老张觉得自己棒棒哒
 */

/**
 * 此处， 非阻塞I/O 系统调用( nonblocking system call ) 和 异步I/O系统调用 （asychronous system call）的区别是：
 * 一个非阻塞I/O 系统调用 read() 操作立即返回的是任何可以立即拿到的数据， 可以是完整的结果， 也可以是不完整的结果， 还可以是一个空值。
 * 而异步I/O系统调用 read（）结果必须是完整的， 但是这个操作完成的通知可以延迟到将来的一个时间点。
 *
 *
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
            listenerChannel.bind(new InetSocketAddress(port));
            listenerChannel.configureBlocking(false);
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听
    public void listener() {
        System.out.println("服务端监听消息");
        try {
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    //有事件处理
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        if (key.isAcceptable()) {
                            //建立与客户端的连接
                            SocketChannel socketChannel = listenerChannel.accept();
                            //注册
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + "上线");
                        }
                        if (key.isReadable()) {
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
                String msg = new String(buffer.array(),0,buffer.limit());
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
            if (target instanceof SocketChannel) {
                SocketChannel desc = (SocketChannel) target;
                //转发消息
                try {
                    String amsg = "服务端回执消息" + msg;
                    Thread.sleep(10000);
                    desc.write(ByteBuffer.wrap(amsg.getBytes()));
                    //这里线程堵塞并不会影响客户端接收数据
                    System.out.println("发送完毕");
                } catch (Exception e) {
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
