package com.ot.nio.transform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class Server {
    private ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
    //使用Map保存每个连接，当OP_READ就绪时，根据key找到对应的文件对其进行写入。
    //若将其封装成一个类，作为值保存，可以再上传过程中显示进度等等
    Map<SelectionKey, FileChannel> fileMap = new HashMap<SelectionKey, FileChannel>();

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer();
    }

    public void startServer() throws IOException {
        //1、获取通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        //2、切换堵塞模式   服务端
        serverChannel.configureBlocking(false);
        //3、绑定连接
        serverChannel.bind(new InetSocketAddress(8888));
        //4、获取选择器
        Selector selector = Selector.open();
        //5、将通道注册到选择器上，并且指定事件类型  监听接收事件
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已开启...");
        // 轮训的方式  在选择器上事件的处理
        while (selector.select() > 0) {
            //	监听到达的事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                //	读写分离
                //	判断连接是否OK    将数据移动到通道中去
                if (key.isAcceptable()) {
                    //	获取客户端连接，相当于开启一个线程去处理
                    SocketChannel socketChannel = serverChannel.accept();
                    if (socketChannel == null) continue;
                    //	切换非堵塞模式
                    socketChannel.configureBlocking(false);
                    //	读的通道注册到选择器上
                    SelectionKey key1 = socketChannel.register(selector, SelectionKey.OP_READ);
                    InetSocketAddress remoteAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
                    File file = new File("d:/" + UUID.randomUUID().toString() + "_" + remoteAddress.getPort() + ".txt");
                    FileChannel fileChannel = new FileOutputStream(file).getChannel();
                    fileMap.put(key1, fileChannel);
                    System.out.println(socketChannel.getRemoteAddress() + "连接成功...");
                    writeToClient(socketChannel);
                } else if (key.isReadable()) {
                    //	读取数据
                    readData(key);
                }
                //	不加的话，很容易出现服务器CPU100%   Linux上发生
                // NIO的特点只会累加，已选择的键的集合不会删除，ready集合会被清空
                // 只是临时删除已选择键集合，当该键代表的通道上再次有感兴趣的集合准备好之后，又会被select函数选中
                it.remove();
            }
        }
    }

    public void writeToClient(SocketChannel socketChannel) throws IOException {
        buffer.clear();
        buffer.put((socketChannel.getRemoteAddress() + "连接成功").getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
    }

    public void readData(SelectionKey key) throws IOException {
        FileChannel fileChannel = fileMap.get(key);
        buffer.clear();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int num = 0;

        while ((num = socketChannel.read(buffer)) > 0) {
            buffer.flip();
            // 写入文件
            fileChannel.write(buffer);
            buffer.clear();
        }
        // 调用close为-1 到达末尾
        if (num == -1) {
            fileChannel.close();
            System.out.println("上传完毕");
            buffer.put((socketChannel.getRemoteAddress() + "上传成功").getBytes());
            buffer.clear();
            socketChannel.write(buffer);
            key.cancel();
        }
    }
}
