package com.ot.netty.dubbo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServer {

    public static void start(int port) {
        start0(port);
    }

    private static void start0(int port) {
        NioEventLoopGroup boos = null;
        NioEventLoopGroup work = null;
        try {
            boos = new NioEventLoopGroup(1);
            work = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boos, work).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new StringEncoder());
                    pipeline.addLast(new StringDecoder());
                    pipeline.addLast(new NettyServerHandler());
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("服务器启动完成....");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            boos.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
