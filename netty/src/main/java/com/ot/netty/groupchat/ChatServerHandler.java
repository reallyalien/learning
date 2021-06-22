package com.ot.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static final Map<String,Channel> channelMap=new ConcurrentHashMap<>();
    //定义一个channel组
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);//全局事件执行器，单例对象
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 第一个被调用的，表示连接建立，
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //添加到map当中
        channelMap.put(channel.remoteAddress().toString(),channel);
        //把该客户建立连接的消息推送给其他客户端
        /**
         * 该方法会将channelGroup中所有的channel遍历并发送消息，我们不需要自己遍历
         */
        Date date = new Date();
        String format = sdf.format(date);
        channelGroup.writeAndFlush("[客户端]：" + channel.remoteAddress() + "在\t" + format + "加入聊天\n");
        channelGroup.add(channel);//把当前channel添加到group
    }

    /**
     * 提示上线 channel处于活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端：" + ctx.channel().remoteAddress() + "上线了\t");
    }

    /**
     * channel处于不活动状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Date date = new Date();
        String format = sdf.format(date);
        System.out.println("客户端：" + ctx.channel().remoteAddress() + " 在 " + format + "离线了\t");
    }

    /**
     * 断开连接触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush("[客户端]：" + ctx.channel().remoteAddress() + "离开\t");
        System.out.println("当前group大小：" + channelGroup.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //读取客户端的消息
        Channel channel = ctx.channel();
        //根据不同的情况回送不同的消息
        channelGroup.forEach((ch -> {
            if (ch != channel) {
                //直接转发
                ch.writeAndFlush("[客户]：" + channel.remoteAddress() + "说：" + msg + "\n");
            } else {
                channel.writeAndFlush("[自己]：" + msg);
            }
        }));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
