package com.ot.netty.simple;

/**
 * 十进制有符号和无符号数在jdk里没有无符号数，
 */
public class Number2 {

    public static void main(String[] args) {
        //这个方法已经将十进制的数字转成补码形式了
        System.out.println(Integer.toBinaryString(-127));//1000 0001
        System.out.println(Integer.toBinaryString(-128));//1000 0000
        System.out.println(Integer.toBinaryString(-0));
        System.out.println(Integer.toBinaryString(+0));
        System.out.println(Integer.toBinaryString(-1)); // 1111 1111
        System.out.println(Integer.toBinaryString(+127));//1111 1111
    }
}
