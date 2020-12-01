package com.ot.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 同步必须等待或者去轮询
 */

/**
 *
 * 一般来说，服务器端的I/O主要有两种情况：
 * 一是来自网络的I/O；
 * 二是对文件(设备)的I/O。
 * 首先一个IO操作其实分成了两个步骤：发起IO请求和实际的IO操作，同步IO和异步IO的区别就在于第二个步骤是否阻塞，如果实际的IO读写阻塞请求进程，那么就是同步IO，因此阻塞IO、非阻塞IO、IO复用、信号驱动IO都是同步IO，如果不阻塞，而是操作系统帮你做完IO操作再将结果返回给你，那么就是异步IO。
 *
 * 阻塞IO和非阻塞IO的区别在于第一步，发起IO请求是否会被阻塞，如果阻塞直到完成那么就是传统的阻塞IO，如果不阻塞，那么就是非阻塞IO。
 * 同步：所谓同步，就是在发出一个功能调用时，在没有得到结果之前，该调用就不返回。
 * 异步：异步的概念和同步相对。当一个异步过程调用发出后，调用者不能立刻得到结果。实际处理这个调用的部件在完成后，通过状态、通知和回调来通知调用者。
 *
 * 一、
 * JAVA NIO是同步非阻塞io。同步和异步说的是消息的通知机制，阻塞非阻塞说的是线程的状态 。
 * client和服务器建立了socket连接：
 * 1、同步阻塞io：client在调用read（）方法时，stream里没有数据可读，线程停止向下执行，直至stream有数据。
 *
 *     阻塞：体现在这个线程不能干别的了，只能在这里等着
 *     同步：是体现在消息通知机制上的，即stream有没有数据是需要我自己来判断的。
 *
 * 2、同步非阻塞io：调用read方法后，如果stream没有数据，方法就返回，然后这个线程就就干别的去了。
 *
 *     非阻塞：体现在，这个线程可以去干别的，不需要一直在这等着
 *     同步：体现在消息通知机制，这个线程仍然要定时的读取stream，判断数据有没有准备好，client采用循环的方式去读取，可以看出CPU大部分被浪费了
 *
 * 3、异步非阻塞io：服务端调用read()方法，若stream中无数据则返回，程序继续向下执行。当stream中有数据时，操作系统会负责把数据拷贝到用户空间，然后通知这个线程，这里的消息通知机制就是异步！而不是像NIO那样，自己起一个线程去监控stream里面有没有数据！
 *
 * 二、 Selector不是异步的。因为它对IO的读写还是同步阻塞的。
 *
 *     selector简称多路复用器，它是JAVA
 *     NIO编程的基础简单来讲，selector会不断轮询注册在其上的channel，如果某个channel上面发生了读或者写事件，这个channel就会处于就绪状态，会被selector轮询出来，然后通过selectorKey可以获取就绪channel的集合，进行后续的io操作。
 *
 * 三、
 * 一般来说 I/O 模型可以分为：同步阻塞，同步非阻塞，异步阻塞，异步非阻塞 四种IO模型
 *
 * 同步阻塞 IO ：
 *
 * 在此种方式下，用户进程在发起一个 IO 操作以后，必须等待 IO 操作的完成，只有当真正完成了 IO 操作以后，用户进程才能运行。 JAVA传统的 IO 模型属于此种方式！
 *
 * 同步非阻塞 IO:
 *
 * 在此种方式下，用户进程发起一个 IO 操作以后 边可 返回做其它事情，但是用户进程需要时不时的询问 IO 操作是否就绪，这就要求用户进程不停的去询问，从而引入不必要的 CPU 资源浪费。其中目前 JAVA 的 NIO 就属于同步非阻塞 IO 。
 *
 * 异步阻塞 IO ：
 *
 * 此种方式下是指应用发起一个 IO 操作以后，不等待内核 IO 操作的完成，等内核完成 IO 操作以后会通知应用程序，这其实就是同步和异步最关键的区别，同步必须等待或者主动的去询问 IO 是否完成，那么为什么说是阻塞的呢？因为此时是通过 select 系统调用来完成的，而 select 函数本身的实现方式是阻塞的，而采用 select 函数有个好处就是它可以同时监听多个文件句柄，从而提高系统的并发性！
 *
 * 异步非阻塞 IO:
 *
 * 在此种模式下，用户进程只需要发起一个 IO 操作然后立即返回，等 IO 操作真正的完成以后，应用程序会得到 IO 操作完成的通知，此时用户进程只需要对数据进行处理就好了，不需要进行实际的 IO 读写操作，因为 真正的 IO读取或者写入操作已经由 内核完成了。目前 Java 中还没有支持此种 IO 模型。
 */
public class Client {

    //定义相关属性
    private static final String host = "127.0.0.1";
    private static final int port = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    //完成初始化工作
    public Client() {
        try {
            this.selector = Selector.open();
            this.socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            this.socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            this.username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + "\t"+"is ok");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    //向服务器发送消息
    public void sendMsg(String msg) {
        try {
            msg = username + "说" + msg;
            ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
            socketChannel.write(buffer);
            System.out.println("发送数据："+msg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    //读取从服务器端的消息
    public void read() {
        try {
            int count = selector.select();
            if (count > 0) {
                //通道有可用的消息
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        //得到一个buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        try {
                            channel.read(buffer);
                            buffer.flip();
                            String msg = new String(buffer.array(),0,buffer.limit());
                            System.out.println(msg.trim());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    iterator.remove();
                }
            } else {
//                System.out.println("没有可用的通道");

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void main(String[] args) {
        //启动客户端
        Client client = new Client();
        //启动一个线程,每个3s去读取数据
        new Thread(() -> {
            while (true) {
                client.read();
                try {
                    TimeUnit.SECONDS.sleep(3);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        //发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String msg = scanner.next();
            client.sendMsg(msg);
        }
    }
}
