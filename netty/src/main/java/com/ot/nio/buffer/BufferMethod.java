package com.ot.nio.buffer;


import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class BufferMethod {

    /**
     * position limit capacity
     */
    private ByteBuffer buffer = ByteBuffer.allocate(20);
    private int time = 1_0000_0000;

    @Test
    public void put() {
        buffer.put((byte) 'a');
        buffer.put((byte) 'b');
//        buffer.put(10, (byte) 'c');//这种方式不会改变position的值
//        byte[] bytes=new byte[3];
//        bytes[0]=(byte)'a';
//        bytes[1]=(byte)'b';
//        bytes[2]=(byte)'c';
//        buffer.put(bytes);
        System.out.println(buffer);
    }

    @Test
    public void get() {
        buffer.put((byte) 'a');
        buffer.put((byte) 'b');
        buffer.put((byte) 'c');
        buffer.put((byte) 'd');
        System.out.println(buffer);
        //读之前转换读写模式
        buffer.flip();
        buffer.get();
        System.out.println(buffer);
        buffer.get();
        System.out.println(buffer);
        buffer.get();
        System.out.println(buffer);
        buffer.get(3);//加索引获取值的方法不会改变position的值
        System.out.println(buffer);
        System.out.println(new String(buffer.array(), 0, buffer.position()));//get并没有清除buffer里的值
    }

    @Test
    public void mark() {
        buffer.position(5);
        System.out.println(buffer);
        buffer.mark();//标记一个position，以便于下次reset重置
        buffer.position(15);
        System.out.println(buffer);
        buffer.reset();
        System.out.println(buffer);
    }

    @Test
    public void rewind() {
        buffer.position(10);
        buffer.limit(15);//限制最大写入位置
        buffer.rewind();//重置position为0
        System.out.println("rewind:" + buffer);
    }

    @Test
    public void compact() { //小型的，紧凑的
        buffer.limit(15);//限制最大写入位
        buffer.put("abc".getBytes());
        System.out.println(buffer);
        buffer.flip();//这里将position的位置
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        System.out.println(buffer);
        System.out.println("compact before " + new String(buffer.array()));
        buffer.compact();//将未读的数据拷贝到buffer的起始处，修改position指向最后一个未读的元素的后面 ，limit指向capacity
        System.out.println("compact after  " + new String(buffer.array()));
        System.out.println(buffer);
    }

    @Test
    public void runtime() throws InterruptedException {
        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);//最大从操作系统拿到的内存，单位MB
        System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);
        System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024);

        TimeUnit.SECONDS.sleep(3);
        for (int i = 0; i < 200000; i++) {
            String[] strings = new String[11111];
        }
        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);
        System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024);
        System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024);
    }

    /**
     * 堆上内存
     */
    @Test
    public void allocate() {
        System.out.println("before allocate:" + Runtime.getRuntime().freeMemory() / 1024 / 1024);
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 1024);
        System.out.println(buffer);
        System.out.println("after allocate:" + Runtime.getRuntime().freeMemory() / 1024 / 1024);
    }

    /**
     * 直接内存
     */
    @Test
    public void allocateDirect() {
        System.out.println("before allocate:" + Runtime.getRuntime().freeMemory() / 1024 / 1024);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 1024);
        System.out.println(buffer);
        System.out.println("after allocate:" + Runtime.getRuntime().freeMemory() / 1024 / 1024);
    }

    @Test
    public void warp() {
        byte[] bytes = new byte[1024];
        ByteBuffer buffer = ByteBuffer.wrap(bytes, 0, 3);
        System.out.println(buffer);
    }
    /**
     * 总结；直接内存虽然分配内存较慢，但是io操作快
     */
    /**
     * 堆上内存和分配时间
     */
    @Test
    public void allocateTime() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            ByteBuffer buffer = ByteBuffer.allocate(2);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);//1218
    }

    /**
     * 直接内存和分配时间 太慢了在数据量大的情况下，很慢很慢
     * 1.直接内存不受java虚拟机所约束，减少垃圾回收，垃圾回收会暂停线程，减少GC，进程间可以共享，减少虚拟机复制时间
     * 2.加快了复制的速度，堆内在flush到远程时，需要先复制到直接内存然后再发送，直接内存就省略了这一步
     * <p>
     * 3.直接内存不好管理，发生内存泄露不好排查
     */
    @Test
    public void allocateDirectTime() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(2);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);  //40895
    }

    @Test
    public void remain() {
        buffer.put("abc".getBytes());
        System.out.println(Arrays.toString(buffer.array()));
    }

    @Test
    public void a() {
        byte[] arr = {1, 2, 3};
        ByteBuffer wrap = ByteBuffer.wrap(arr);
        System.out.println(wrap);
    }
}
