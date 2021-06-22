package com.ot.netty.codec4;

import io.netty.buffer.ByteBuf;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1l;
    private String username;
    private int userId;

    public byte[] code() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] bytes = this.username.getBytes();
        buffer.putInt(bytes.length);
        buffer.put(bytes);
        buffer.putInt(this.userId);
        buffer.flip();
        //flip之后，position指向0，limit就是实际长度大小
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    /***
     * jdk序列化比byte序列化很废内存
     * @throws IOException
     */
    @Test
    public void testLength() throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.username = "welcome to netty";
        userInfo.userId = 100;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(userInfo);
        objectOutputStream.flush();
        objectOutputStream.close();
        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println("jdk  序列化：" + bytes.length);
        System.out.println("byte 序列化：" + userInfo.code().length);
    }

    /***
     * jdk序列化比byte序列化很废时间
     * @throws IOException
     */
    @Test
    public void testTime() throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.username = "welcome to netty";
        userInfo.userId = 100;
        int loop = 100_0000;
        ByteArrayOutputStream byteArrayOutputStream;
        ObjectOutputStream objectOutputStream;
        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(userInfo);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("jdk 序列化：" + (end - start));
        System.out.println("===========================================");
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            userInfo.code();
        }
        long end1 = System.currentTimeMillis();
        System.out.println("byte 序列化：" + (end1 - start1));
    }

}
