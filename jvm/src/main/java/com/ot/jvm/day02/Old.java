package com.ot.jvm.day02;

/**
 * 大对象直接分配到老年代
 * -XX:PretenureSizeThreshold=6M
 */
public class Old {
    public static void main(String[] args) {
        byte[] b1 = new byte[7 * 1024 * 1024];
    }
}
