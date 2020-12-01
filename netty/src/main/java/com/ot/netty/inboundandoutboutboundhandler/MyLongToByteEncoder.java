package com.ot.netty.inboundandoutboutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    /**
     * 发送数据的类型与指定类型不一致，不会去编码
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder被调用");
        System.out.println("msg:"+msg);
        out.writeLong(msg);
    }
}
