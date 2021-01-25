package com.ot.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class NettyBuf04 {

    public static void main(String[] args) {
        //这种方式创建的其实是NIO的buffer
        ByteBuf buffer = Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8);
        //这种方式创建的是netty的buffer
        ByteBuf buffer1 = Unpooled.copiedBuffer("hello".getBytes(CharsetUtil.UTF_8));
        System.out.println(buffer.readableBytes());
        System.out.println(buffer1.readableBytes());
    }
}
