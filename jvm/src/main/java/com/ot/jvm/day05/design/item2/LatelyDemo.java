package com.ot.jvm.day05.design.item2;

import java.math.BigInteger;

/**
 * @Title: LatelyDemo
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/22
 * @Version: 1.0
 */
public class LatelyDemo {
    static BigInteger fibonacci01(int num) {
        BigInteger x = BigInteger.ZERO;
        BigInteger y = BigInteger.ONE;
        BigInteger z;
        for(int i = 0; i < num; i++) {
            z = y;
            y = x.add(y);
            x = z;
        }
        return x;
    }

    static BigInteger fibonacci02(int num) {
        BigInteger x = BigInteger.ZERO;
        BigInteger y = BigInteger.ONE;

        for(int i = 0; i < num; i++) {
            BigInteger z = y;
            y = x.add(y);
            x = z;
        }
        return x;
    }

    public static void main(String[] args) {
        System.out.println("测试就近原则");
        Long startTimeMillis = System.currentTimeMillis();
        //Integer.MAX_VALUE
        LatelyDemo.fibonacci01(10000);
        System.out.println(System.currentTimeMillis() - startTimeMillis);

        System.out.println("===============华丽的分割线==============");

        Long startTimeMillis01 = System.currentTimeMillis();
        LatelyDemo.fibonacci02(10000);
        System.out.println(System.currentTimeMillis() - startTimeMillis01);
    }
}
