package com.ot.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("客户端channelActive的pipeline().hashCode()："+ctx.pipeline().hashCode());
//        System.out.println("客户端channelActive的channel().hashCode()："+ctx.pipeline().channel().hashCode());
        //使用客户端发送10条数据
        String msg = "今天天气冷，吃火锅";
        for (int i = 0; i < 20; ++i) {
            byte[] bytes = msg.concat("" + i).getBytes(CharsetUtil.UTF_8);
            int length = bytes.length;
            //创建协议包
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(bytes);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常消息：" + cause.getMessage());
        ctx.close();
    }

    //mask_channel_unregistered
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
//        System.out.println("客户端channelRead0的pipeline().hashCode()："+ctx.pipeline().hashCode());
//        System.out.println("客户端channelRead0的channel().hashCode()："+ctx.pipeline().channel().hashCode());
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("客户端接收到数据:" + "len=" + len + ",content=" + new String(content, CharsetUtil.UTF_8));
        System.out.println("客户端接收的次数：" + (++this.count));
    }
}
