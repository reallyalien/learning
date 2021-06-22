package com.ot.jvm.day03;

/**
 *默认的堆大小是机器内存的1/64
 * 可达性分析法，当c1和c2两个对象断掉引用之后，他们还在互相引用，各自的ji'shu
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

