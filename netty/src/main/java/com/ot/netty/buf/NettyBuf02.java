package com.ot.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.Arrays;

public class NettyBuf02 {

    public static void main(String[] args) {
//        ByteBuf buffer = Unpooled.copiedBuffer("hello,北京".getBytes(CharsetUtil.UTF_8));
        ByteBuf buffer = Unpooled.copiedBuffer("hello,北京",CharsetUtil.UTF_8);
        //使用相关的api
        if (buffer.hasArray()) {
            byte[] array = buffer.array();
            String s = new String(array, 0, buffer.readableBytes(), CharsetUtil.UTF_8);
            System.out.println(s);

        }
        System.out.println(buffer);
        System.out.println("可读取的字节数：" + buffer.readableBytes());
        System.out.println("读取一段：" + buffer.getCharSequence(0, 12, CharsetUtil.UTF_8));
    }

    /**
     * hello,北京
     * UnpooledHeapByteBuf(ridx: 0, widx: 12, cap: 12/12)
     * 可读取的字节数：12
     * 读取一段：hello,北京
     */

    /**
     * hello,北京
     * UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 12, cap: 24)
     * 可读取的字节数：12
     * 读取一段：hello,北京
     */
}
