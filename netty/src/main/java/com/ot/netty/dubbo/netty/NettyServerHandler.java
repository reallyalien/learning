package com.ot.netty.dubbo.netty;

import com.ot.netty.dubbo.api.AppServiceImpl;
import com.ot.netty.dubbo.constant.ProtocolHeader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (msg.startsWith(ProtocolHeader.HEADER)) {
            String app = new AppServiceImpl().app(msg.replace(ProtocolHeader.HEADER, ""));
            ctx.writeAndFlush(app);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
