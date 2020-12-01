package com.ot.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class NettyBuf04 {

    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8);
        ByteBuf buffer1 = Unpooled.copiedBuffer("hello".getBytes(CharsetUtil.UTF_8));
        System.out.println(buffer.readableBytes());
        System.out.println(buffer1.readableBytes());
    }
}
