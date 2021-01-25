package com.ot.netty.simple;

/**
 * 十进制有符号和无符号数
 */
public class Number3 {

    public static void main(String[] args) {
        byte b = Byte.parseByte("10000001", 2);
        Byte aByte = Byte.valueOf("10000001", 2);
        System.out.println(b);
        System.out.println(aByte);
    }
}
