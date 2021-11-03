package com.ot.noline.tree;

public class B {

    public static void main(String[] args) {
        byte a = -127;
        String s = Integer.toBinaryString(a);
        System.out.println(s);
        String ss = "10000001";
        byte i = (byte) Integer.parseInt(ss, 2);
        System.out.println(i);
    }
}
