package com.ot.netty.websocket;

import com.ot.netty.heartbeat.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class WebSocketServer {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            /**
                             * netty底层仍然是java的序列化，无法跨语言，序列化后体积较大，是二进制编码的5倍多，序列化性能低
                             */
                            pipeline.addLast(new HttpServerCodec()); //因为基于http协议，使用http的编解码器
                            pipeline.addLast(new ChunkedWriteHandler());//以块的方式处理
                            /**
                             * 1.http的数据在传输过程中是分段的，HttpObjectAggregator就是可以将多个段聚合起来
                             * 这就是为什么浏览器发送大量数据时就会发出多次http请求的原因
                             *
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * 1.对于webSocket，数据是以帧的形式传递
                             * 2.WebSocketFrame下面有6个子类
                             * 3.浏览器请求时：ws://localhost:7000/xxx
                             * 4.WebSocketServerProtocolHandler把http协议升级为ws协议,保持长连接
                             * 5.101协议
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            pipeline.addLast(new TextWebSocketFrameHandler());//自定义
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
