package com.ot.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当通道就绪时，就会触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       //发送一个student对象发送到服务端
        StudentPojo.Student student = StudentPojo.Student.newBuilder().setId(4).setName("小明").build();
        ctx.writeAndFlush(student);
    }

    /**
     * 有数据可读时，触发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer= (ByteBuf) msg;
        System.out.println("服务器回复的消息："+buffer.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址："+ctx.channel().remoteAddress().toString().substring(1));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
