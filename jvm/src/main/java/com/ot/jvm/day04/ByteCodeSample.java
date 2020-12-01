package com.ot.jvm.day04;

import com.sun.org.apache.bcel.internal.generic.GOTO;

/**
 * @Title: ByteCodeSample
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/13
 * @Version: 1.0
 */
public class ByteCodeSample {
    private  String msg = "hello world";

    public void say() {
        String msg2 = "abc";
        System.out.println(msg);
    }

}
