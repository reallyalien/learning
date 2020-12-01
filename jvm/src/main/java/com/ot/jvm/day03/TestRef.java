package com.ot.jvm.day03;

import java.lang.ref.SoftReference;

/**
 */
public class TestRef {
    public static void main(String args[]) {

        byte[] b1 = new byte[1 * 1024*1024];

        //通知JVM进行内存回收
        System.gc();
        SoftReference<String> str = new SoftReference<String>(new String("abc"));
        System.out.println(str.get());

    }
}
