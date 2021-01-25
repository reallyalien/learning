package com.ot.noline.tree;

public class A {
    public static void main(String[] args) {
        String str = "11111111";
        System.out.println((byte) Integer.parseInt(str, 2));
        System.out.println("=================");
        int a = -1;
        System.out.println(Integer.toBinaryString(a));//返回的是补码
    }
}
