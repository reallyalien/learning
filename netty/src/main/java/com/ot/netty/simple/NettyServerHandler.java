package com.ot.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


/**
 * 我们自定义一个handler，这时我们自定义的handler才能称为一个handler
 * 再数据传输过程当中 只能以buffer的形式传输，如果你直接发送的就是buffer，则就不需要编解码器的存在了
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * @param ctx 上下文对象，含有pipeline，channel，address
     * @param msg 客户端发送的消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程："+Thread.currentThread());
//        System.out.println("server ctx:" + ctx);
//        System.out.println("channel与pipeline的关系");
//        System.out.println("ctx.channel():"+ctx.channel());
//        System.out.println("ctx.pipeline():"+ctx.pipeline());
//        System.out.println("ctx.channel().pipeline():"+ctx.channel().pipeline());
//        System.out.println(ctx.channel().eventLoop());
//        //将对象转成byteBuffer
        ByteBuf buffer = (ByteBuf) msg;
        System.out.println("客户端发送消息：" + buffer.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress().toString().substring(1));
        //这里有一个特别耗费时间的操作，提交到该channel的eventLoop的taskQueue当中
        /**
         * 解决方案1：用户程序自定义普通任务
         */
        EventLoop eventLoop = ctx.channel().eventLoop();
//        eventLoop.execute(()->{
//            try {
//                Thread.sleep(10 * 1000);
//            } catch (InterruptedException e) {
//                System.out.println("发生异常："+e.getMessage());
//            }
//            ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端，我是服务器自定义普通任务1 ".getBytes(CharsetUtil.UTF_8)));
//        });
//        eventLoop.execute(()->{
//            try {
//                Thread.sleep(20 * 1000);
//            } catch (InterruptedException e) {
//                System.out.println("发生异常："+e.getMessage());
//            }
//            ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端，我是服务器自定义普通任务2 ".getBytes(CharsetUtil.UTF_8)));
//        });
//        /**
//         * 解决方案2：用户自定义定时任务
//         */
//        eventLoop.schedule(()->{
//            try {
//                Thread.sleep(10*1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端，我是服务器自定义普通任务3 ".getBytes(CharsetUtil.UTF_8)));
//        },5, TimeUnit.SECONDS);
        System.out.println("go on....");
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
