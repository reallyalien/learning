package com.ot.netty.codec2;

import com.ot.netty.codec.StudentPojo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;


public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当通道就绪时，就会触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //随机发送一个student对象或者worker发送到服务端
        int i = new Random().nextInt(3);
        OutClass.MyMessage message = null;
        if (i == 0) {
            message=OutClass.MyMessage.newBuilder().setDataType(OutClass.MyMessage.DataType.StudentType)
                    .setStudent(OutClass.Student.newBuilder().setId(1).setName("宋江").build()).build();
        }else {
            //发送worker
            message=OutClass.MyMessage.newBuilder().setDataType(OutClass.MyMessage.DataType.WorkerType)
                    .setWorker(OutClass.Worker.newBuilder().setAge(20).setName("大傻").build()).build();
        }
        ctx.writeAndFlush(message);
    }

    /**
     * 有数据可读时，触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        System.out.println("服务器回复的消息：" + buffer.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址：" + ctx.channel().remoteAddress().toString().substring(1));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
