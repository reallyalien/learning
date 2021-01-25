package com.ot.netty.ctx;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;

import java.nio.charset.Charset;

public class ServerHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("当前服务端2的io线程："+Thread.currentThread());
        EventLoop eventExecutors = ctx.pipeline().channel().eventLoop();
        System.out.println("当前服务端2的eventloop线程："+eventExecutors);
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        System.out.println("服务端2接收到消息:" + new String(bytes, Charset.defaultCharset()));
        super.channelRead(ctx, msg);
    }


}
