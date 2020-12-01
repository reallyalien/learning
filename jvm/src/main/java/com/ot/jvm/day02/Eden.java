package com.ot.jvm.day02;

/**
 * 优先分配到Eden
 */
public class Eden {
    public static void main(String[] args) {
        // -verbose:gc -XX:+PrintGCDetails -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8
//
//        byte[] b1 = new byte[2 * 1024 * 1024];
//        byte[] b2 = new byte[2 * 1024 * 1024];
//        byte[] b3 = new byte[2 * 1024 * 1024];//第一次minor gc
        byte[] b4 = new byte[4 * 1024 * 1024]; //eden有空间，就直接分配
//
//        System.gc();

        /**
         * -XX:PretenureSizeThreshold=2m此参数需要跟下面的参数一起使用否则无效果
         * -XX:+UseSerialGC
         */
    }
}
