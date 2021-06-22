package com.ot.aio;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Scanner;

/**
 * 同步和异步：
 * 针对IO操作，如果此操作是由java自己完成的，那么这个操作就是同步操作
 * 针对IO操作，如果此操作是由操作系统完成的，并在处理完成后通知我们的应用程序，所以我们的应用程序可以继续做其他事情，那么这个操作是异步
 * 阻塞和非阻塞：
 * 针对某些方法，如果此方法在执行时会发生阻塞等待，那么此方法就是阻塞的
 * 针对某些方法，如果此方法在执行时不会发生阻塞，而是不管操作是否执行成功，都会立即返回一个状态值，那么此方法就是非阻塞的
 */
public class AioServerHandler implements CompletionHandler<AsynchronousSocketChannel, AioServer> {

    private static int PORT = 123456;


    public static void main(String[] args) {

    }

    /**
     * 业务处理逻辑：当请求到来的时候，监听成功，应该做什么事情
     * 一定要实现的逻辑：为下一次客户端请求开启监听，调用accept()
     *
     * @param result     和客户端直接关联的通道，如操作系统准备好的读取数据缓存，或等待返回数据的缓存
     * @param attachment
     */
    @Override
    public void completed(AsynchronousSocketChannel result, AioServer attachment) {
        //处理下一次客户端请求
        attachment.getServerChannel().accept(attachment, this);
    }

    public void doRead(final AsynchronousSocketChannel channel) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            /**
             * 业务逻辑，读取客户端传输数据
             * @param result
             * @param attachment 在complete方法执行的时候，操作系统已经将客户端请求的数据放入Buffer的缓存当中，使用前要flip
             */
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println(attachment.capacity());
                //复位操作
                attachment.flip();
                try {
                    System.out.println("from client :" + new String(buffer.array(), "utf-8"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
    }

    /**
     * 真实项目中，服务器返回的结果应该是根据客户端的请求计算得到的。
     *
     * @param channel
     */
    private void doWriter(AsynchronousSocketChannel channel) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("enter message send to client > ");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        try {
            buffer.put(line.getBytes("UTF-8"));
            // 必须复位
            buffer.flip();
            // writer()方法是异步操作，具体实现由操作系统实现。可增加get()方法，实现阻塞，等待操作系统的写操作结束
            channel.write(buffer);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异常处理逻辑，当服务端代码出现异常的时候，应该做什么事情
     *
     * @param exc
     * @param attachment
     */
    @Override
    public void failed(Throwable exc, AioServer attachment) {

    }

}
