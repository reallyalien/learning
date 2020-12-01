package com.ot.netty.codec2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;


/**
 * 我们自定义一个handler，这时我们自定义的handler才能称为一个handler
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<OutClass.MyMessage> {
    /**
     * @param ctx 上下文对象，含有pipeline，channel，address
     * @param msg 客户端发送的消息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OutClass.MyMessage msg) throws Exception {
        OutClass.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == OutClass.MyMessage.DataType.StudentType) {
            OutClass.Student student = msg.getStudent();
            System.out.println(student.getId() + "\t" + student.getName());
        } else if (dataType == OutClass.MyMessage.DataType.WorkerType) {
            OutClass.Worker worker = msg.getWorker();
            System.out.println(worker.getAge()+"\t"+worker.getName());
        } else {
            System.out.println("请检查类型");
        }

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
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端，我是服务器 ", CharsetUtil.UTF_8));
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
        ctx.close();
    }
}
