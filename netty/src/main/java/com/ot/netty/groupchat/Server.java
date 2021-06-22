package com.ot.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {

    private final int port;//监听端口

    public Server(int port) {
        this.port = port;

    }

    //编写一个run方法，处理客户端请求
    public void run() throws InterruptedException {
        //创建2个线程组
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(1);//1个线程
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();//8个线程
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decode", new StringDecoder());//加入netty的解码器
                            pipeline.addLast("encode", new StringEncoder());//加入编码器
                            pipeline.addLast(new ChatServerHandler());
                        }
                    });
            System.out.println("netty服务器启动..");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //监听关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server(7000);
        server.run();
    }
}
