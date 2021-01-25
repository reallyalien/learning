package com.ot.netty.ctx;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class ServerHandler1 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("当前服务端1的io线程："+Thread.currentThread());
        EventLoop eventExecutors = ctx.pipeline().channel().eventLoop();
        System.out.println("当前服务端1的eventloop线程："+eventExecutors);
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        System.out.println("服务端1接收到消息:" + new String(bytes, Charset.defaultCharset()));
        //消息在handler当中以byteBuff传递，在handler1处理完之后，readIndex与write一致，不重置直接传递给下一个handler导致接收不到数据
        buf.resetReaderIndex();
        super.channelRead(ctx, buf);
    }
}
