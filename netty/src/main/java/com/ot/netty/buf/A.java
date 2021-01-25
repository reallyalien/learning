package com.ot.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class A {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);
        buffer.writeInt(3);
        buffer.writeBytes("abc".getBytes());

        System.out.println("==============");

        int i = buffer.readInt();
        byte[] arr=new byte[i];
        ByteBuf buf = buffer.readBytes(arr);
        System.out.println(buf);
    }
}
