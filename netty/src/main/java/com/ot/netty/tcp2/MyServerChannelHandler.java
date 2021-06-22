package com.ot.netty.tcp2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MyServerChannelHandler extends SimpleChannelInboundHandler<String> {
    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("服务器接收到数据 " + msg);
        System.out.println("服务器接收到消息量=" + (++this.count));
        //服务器回送数据给客户端, 回送一个随机id ,
        String s = UUID.randomUUID().toString() + "\t\t" + "\r\n";
        ctx.writeAndFlush(s);
    }
}
