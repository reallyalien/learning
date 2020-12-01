package com.ot.jvm.day03;

/**
 *
 */
public class Count {
    private Object instance;

    public Count() {
        // 占据20M内存
        byte[] m = new byte[20 * 1024 *1024];
    }

    public static void main(String[] args) {
        Count c1 = new Count();
        Count c2 = new Count();

        c1.instance = c2;
        c2.instance = c1;
        // 断掉引用
        c1 = null;
        c2 = null;

        //垃圾回收
        System.gc();
    }
}

