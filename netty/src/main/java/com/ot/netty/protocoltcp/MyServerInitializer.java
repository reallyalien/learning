package com.ot.netty.protocoltcp;



import com.ot.netty.tcp2.MyServerChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * @see MyServerChannelHandler#hashCode()
 * @see MyServerChannelHandler#channelRead0(ChannelHandlerContext, String)
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MyMessageEncoder());//编码器
        pipeline.addLast(new MyMessageDecoder());//解码器
        pipeline.addLast(new MyServerHandler());

    }
}
