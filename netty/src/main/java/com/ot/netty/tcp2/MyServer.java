package com.ot.netty.tcp2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyServer {

    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(boosGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerChannelInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(7777).sync();
            System.out.println("服务器启动成功");
            channelFuture.channel().closeFuture().sync();

        }catch (Exception e){
            workGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
        }
    }
}
