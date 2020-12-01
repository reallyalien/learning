package com.ot.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            //创建服务器端启动器,
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置启动参数
            serverBootstrap
                    .group(bossGroup, workGroup)//设置2个线程组
                    .channel(NioServerSocketChannel.class) //设置NioServerSocketChannel作为服务器通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到连接的个数 //设置服务器端的通道选项
//                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置整个连接保持长连接 //设置与客户端连接的通道选项
                   // .handler(null)//是指该handler对应的是bootGroup，childrenHandler是对应的workGroup
                    .childHandler(new HttpServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(6661).sync(); //sync等待异步操作完成，其实让netty的异步io变成同步io
            channelFuture.channel().closeFuture().sync();
        } finally {
            //断开连接，关闭线程
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
