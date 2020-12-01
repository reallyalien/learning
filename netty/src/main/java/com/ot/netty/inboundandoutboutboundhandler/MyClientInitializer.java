package com.ot.netty.inboundandoutboutboundhandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    /**                                 出站
     *                            MyClientHandler-----MyLongToByteEncoder
     * 出站            服务器 <-------------------- 客户端
     * @param ch
     * @throws Exception
     */

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyLongToByteEncoder());//出站编码器
        pipeline.addLast(new MyByteToLongDecoder());//入站解码器
        pipeline.addLast(new MyClientHandler());
        //加入出站的handler进行编码和自定义的handler

    }
}
