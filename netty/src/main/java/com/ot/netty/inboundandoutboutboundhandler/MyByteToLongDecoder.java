package com.ot.netty.inboundandoutboutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码器
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     * decoder会被调用多次，直至没有元素添加到list，这个传递到下一个业务handler也会被调用多次
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //long8个字节
        System.out.println("MyByteToLongDecoder被调用");
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
