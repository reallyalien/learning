package com.ot.netty.http2;

import http.lyx3.HttpDemoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {

    //文件服务器的根目录
    private static final String DEFAULT_URL="/src/com/ot/";
    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("http-decoder", new HttpRequestDecoder());
                            //将多个消息转化为单一的HttpRequest或者httpResponse,原因是http解码器在每个http消息中会生成多个消息对象
                            // pipeline.addLast("http-aggregator", new HttpObjectAggregator(65535));
                            pipeline.addLast("http-response", new HttpResponseEncoder());
                            //支持异步发送大的码流，但不占用过多的内存，防止内存溢出
                            pipeline.addLast("http-chunk", new ChunkedWriteHandler());
                            pipeline.addLast("http-fileHandler", new HttpDemoServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(7777).sync();
            System.out.println("服务器启动");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
