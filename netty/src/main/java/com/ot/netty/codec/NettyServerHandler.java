package com.ot.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


/**
 * 我们自定义一个handler，这时我们自定义的handler才能称为一个handler
 * 我们在发送数据的时候没有进行任务编码，因此我们添加的ProtoHandler就会处理我们的数据
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * @param ctx 上下文对象，含有pipeline，channel，address
     * @param msg 客户端发送的消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读取从客户端发送的数据
        StudentPojo.Student student= (StudentPojo.Student) msg;
        System.out.println("客户端发送的数据："+student.getId()+"\t"+student.getName());
    }

    /**
     * 数据读取完毕 回复消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入到缓冲并刷新到管道
        //一般来讲，我们对这个发送的数据进行编码
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端，我是服务器 ".getBytes(CharsetUtil.UTF_8)));
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端，我是服务器 ",CharsetUtil.UTF_8));
    }

    /**
     * 处理异常 需要关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
