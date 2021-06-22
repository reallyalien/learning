package com.ot.netty.codec3.delimiterbase;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private int count;
    private static final String msg = "hello,server,welcome to netty.$_";

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端第" + (++this.count) + "接收消息,消息内容：" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
//            ctx.writeAndFlush(Unpooled.copiedBuffer(msg.getBytes()));
            ctx.writeAndFlush(msg);
        }

    }
}
