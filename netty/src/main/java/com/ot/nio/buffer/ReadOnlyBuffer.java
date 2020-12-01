package com.ot.nio.buffer;

import java.nio.ByteBuffer;

public class ReadOnlyBuffer {

    public static void main(String[] args) {
        ByteBuffer buffer=ByteBuffer.allocate(20);
        for (byte i = 0; i < 20; i++) {
            buffer.put(i);
        }
        //读取
        buffer.flip();
        //得到一个只读的buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
        /**
         * ReadOnlyBufferException
         */
        //readOnlyBuffer.put((byte) 1);
    }
}
