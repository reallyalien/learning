package com.ot.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //cause.printStackTrace();
        ctx.close();
    }

    /**
     * 由于服务端一次读取到的字节数是不确定，所以造成拆开喝粘包情况
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
//        System.out.println("服务端channelRead0的pipeline().hashCode()："+ctx.pipeline().hashCode());
//        System.out.println("服务端channelRead0的channel().hashCode()："+ctx.pipeline().channel().hashCode());
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务端接收到数据:" + "len=" + len + ",content=" + new String(content, CharsetUtil.UTF_8));
        System.out.println("服务端接收的次数：" + (++this.count));
        String responseContent=UUID.randomUUID().toString();
        int length = responseContent.getBytes(CharsetUtil.UTF_8).length;
        //构建协议包
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(responseContent.getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(messageProtocol);
    }
}
