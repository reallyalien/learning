package com.ot.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**                        出站
 *             ------> handler---------> decoder------------>encoder
 *   client
 *            <------handler<--------- decoder<------------encoder
 *                         入站
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MyMessageEncoder());//加入编码器   出站
        pipeline.addLast(new MyMessageDecoder());//加入解码器   入站

        pipeline.addLast(new MyClientHandler());         //     入站

    }
}
