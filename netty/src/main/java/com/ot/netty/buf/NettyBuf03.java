package com.ot.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class NettyBuf03 {

    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8);
        byte[] bytes = new byte[buffer.readableBytes()];
        buffer.readBytes(bytes);
        String s = new String(bytes, CharsetUtil.UTF_8);
        System.out.println(s);

        //===============================================================================

        String s1 = new String(buffer.array(), CharsetUtil.UTF_8);
        System.out.println(s1);
    }
}
