package com.ot.netty.inboundandoutboutboundhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务器回送信息："+msg);
    }

    /**
     * 发送数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("myClientHandler发送数据："+"pipeline的哈希码"+ctx.pipeline().hashCode()+"ctx的哈希码"+ctx.handler());
        ctx.writeAndFlush(123L);//发送一个long
        //当发送一个字符串的时候，不会经过编码处理器，因为编码处理器会判断当前数据的类型是不是应该处理的类型，如果是，进行编码
        //如果不是直接发送
    }
}
