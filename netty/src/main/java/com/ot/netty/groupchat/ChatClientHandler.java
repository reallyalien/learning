package com.ot.netty.groupchat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("当前处理的io线程："+ctx.pipeline().channel().eventLoop().hashCode());
        System.out.println(ctx.channel().remoteAddress()+"服务器发送的消息："+msg.trim());
    }
}
