package com.ot.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入netty提供的一个httpServerCodec编解码器{coder decoder}
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        //增加一个自定义的处理器
        pipeline.addLast("MyHttpServerHandler",new HttpServerHandler());
    }
}
