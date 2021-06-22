package com.ot.aio;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.*;

/**
 * 异步非阻塞
 */
public class AioServer {
    private static final Integer DEFAULT_PORT = 9999;

    private ExecutorService service;

    /**
     * 服务端通道，针对服务端定义的端口号
     */
    private AsynchronousServerSocketChannel serverSocketChannel;


    public AioServer(int port) {
        init(port);
    }

    public void init(int port) {
        System.out.println("server starting at port:" + port + "......");
        InetSocketAddress ip = new InetSocketAddress(port);
        service = new ThreadPoolExecutor(4,
                4,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(),
                r -> new Thread(r));

        try {
            //开启服务
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            //绑定端口
            serverSocketChannel.bind(ip);
            System.out.println("server start");
            serverSocketChannel.accept(this, new AioServerHandler());
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ExecutorService getService() {
        return service;
    }

    public void setService(ExecutorService service) {
        this.service = service;
    }

    public AsynchronousServerSocketChannel getServerChannel() {
        return serverSocketChannel;
    }

    public void setServerChannel(AsynchronousServerSocketChannel serverChannel) {
        this.serverSocketChannel = serverChannel;
    }

    public static void main(String[] args) {
        AioServer aioServer = new AioServer(DEFAULT_PORT);
    }
}
