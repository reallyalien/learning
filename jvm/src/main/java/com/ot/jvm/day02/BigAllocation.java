package com.ot.jvm.day02;


/**
 * 大对象直接进入老年代
 * -Xms20m -Xmx20m  -Xmn10m -XX:+PrintGCDetails
 * -XX:PretenureSizeThreshold=2m        默认值是0,超过这个值的时候，对象直接在old区分配内存
 * -XX:+UseSerialGC   //使用串行回收器进行回收
 * -XX:MaxTenuringThreshold对象年龄设置
 */
public class BigAllocation {
    private static final int _1MB = 1024 * 1024; //1M的大小

    // * 大对象直接进入老年代(Eden  2m  +1 )
    public static void main(String[] args) {
        byte[] b1, b2, b3;
        b1 = new byte[1 * _1MB]; //这个对象在eden区
        b2 = new byte[1 * _1MB]; //这个对象在eden区
        b3 = new byte[5 * _1MB];//这个对象直接进入老年代
    }
}
