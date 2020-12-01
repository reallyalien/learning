package com.ot.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyServer {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))//在boosGroup增加一个日志处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            /**
                             * 1.netty处理空闲状态的处理器
                             * 2.readerIdleTime：时间到了，发送心跳检测包，
                             * 3.后面同样的道理
                             * 4. Triggers an {@link IdleStateEvent} when a {@link Channel} has not performed read, write, or both operation for a while.
                             * 5.IdleStateEvent触发后，就会传递给管道pipeline下一个ctx的handler去处理，通过调用触发下一个handler的userEventTrigger()方法，
                             * 在该方法去处理IdleStateEvent时间，可能是读空闲，写空闲，读写空闲
                             */
                            pipeline.addLast(new IdleStateHandler(10,5000,7000, TimeUnit.SECONDS));//加入netty提供的idle
                            //加入对空闲检测进一步处理的自定义的handler
                            pipeline.addLast(new MyServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
