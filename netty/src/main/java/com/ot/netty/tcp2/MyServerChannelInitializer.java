package com.ot.netty.tcp2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class MyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //换行符作为分割
        pipeline.addLast(new LineBasedFrameDecoder(1024));
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new StringDecoder());


        pipeline.addLast(new MyServerChannelHandler());
    }
}
