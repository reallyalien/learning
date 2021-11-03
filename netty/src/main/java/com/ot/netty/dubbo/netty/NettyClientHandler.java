package com.ot.netty.dubbo.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

public class NettyClientHandler extends SimpleChannelInboundHandler<String> implements Callable {

    private ChannelHandlerContext ctx = null;

    private String param = null;
    private String result = null;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }

    @Override
    protected synchronized void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        this.result = msg;
        notify();
    }

    @Override
    public synchronized Object call() throws Exception {
        ctx.writeAndFlush(this.param);
        wait();
        return result;
    }

    public void setParam(String param) {
        this.param = param;
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
