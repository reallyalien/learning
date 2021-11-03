package com.ot.netty.zero;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

public class Test1 {
    public static void main(String[] args) {
        //其实这种方式netty的buffer包装了这个数组，使用的是同一块内存空间，避免了数据拷贝
        ByteBuf head = Unpooled.wrappedBuffer("hello".getBytes(StandardCharsets.UTF_8));
        ByteBuf body = Unpooled.wrappedBuffer("world".getBytes(StandardCharsets.UTF_8));
        //合并buffer
        CompositeByteBuf compositeBuffer = Unpooled.compositeBuffer();
        compositeBuffer.addComponents(true, head, body);
        Unpooled.wrappedBuffer(head, body);
    }
}
