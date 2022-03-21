package com.ot.nio.buffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * buffer的分散和聚合
 * 将数据写入到buffer时，可以采用buffer依次写入，一个满了接着下一个：分散
 * 将数据从buffer读出到channel时，可以采用buffer，依次读  聚集
 */
public class ScatteringAndGathering {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9999));

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        //监听,获取客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int maxLength = 8;//假定从客户端接收8个字节
        //循环读取
        while (true) {
            int len = 0;
            while (len < maxLength) {
                long l = socketChannel.read(byteBuffers);//读不到数据，阻塞在这里
                len += l;//累计读取字节数
                System.out.println("read:" + len);
                //使用流打印
                Arrays
                        .asList(byteBuffers)
                        .stream()
                        .map(buffer -> "position= " + buffer.position() +"\t"+ "limit=" + buffer.limit())
                        .forEach(System.out::println);
            }
            //将所有的buffer反转，改变模式
            Arrays.asList(byteBuffers).stream().forEach(byteBuffer -> byteBuffer.flip());
            //将数据读出，显示客户端
            int len1 = 0;
            while (len1 < maxLength) {
                long l = socketChannel.write(byteBuffers);
                len1+=l;
                System.out.println("write:"+len1);

            }
            //将所有的buffer复位
            Arrays.asList(byteBuffers).stream().forEach(byteBuffer -> byteBuffer.clear());
            System.out.println("byte read="+len+"\t"+"byte write"+len1);
        }
    }
}
