package com.ot.jvm.day02;

/**
 * 对象优先在Eden分配
 * -Xms20m -Xmx20m  -Xmn10m -XX:+PrintGCDetails
 */
public class EdenAllocation {
    private static final int _1MB = 1024 * 1024; //1M的大小

    //   private final String str = "Fame";
    // * 对象优先在Eden分配
//    public static String str="hello";
    public static void main(String[] args) {
        byte[] b1, b2, b3, b4;
        b1 = new byte[1 * _1MB];
        b2 = new byte[1 * _1MB];
        b3 = new byte[1 * _1MB];
        b4 = new byte[1 * _1MB];
    }
    /**
     * 无任何成员变量或静态变量：
     * Metaspace       used 3213K, capacity 4496K, committed 4864K, reserved 1056768K
     *
     */
}
