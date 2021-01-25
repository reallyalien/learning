package com.ot.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyBuf01 {

    public static void main(String[] args) {
        //创建一个buffer对象，包含一个数组 byte[10]
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        System.out.println(buffer);
        //在netty，不需要flip反转,底层维护了一个readIndex和writeIndex
        //readIndex：下一次读的位置，put数据不改变
        //writeIndex：下一次写的范围，put一次，增加一次
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }
        System.out.println(buffer);
    }
}
