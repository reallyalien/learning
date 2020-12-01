package com.ot.netty.inboundandoutboutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("从客户端读取到的消息："+msg.toString()+"pipeline的哈希码"+ctx.pipeline().hashCode()+"ctx的哈希码"+ctx.handler());
        //给客户端发送一个long
        ctx.writeAndFlush(98765l);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
