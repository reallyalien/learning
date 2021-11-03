package com.ot.noline.tree;

public class A {
    public static void main(String[] args) {
        c((byte) 127);
    }

    public static void c(byte c) {
        String b = b(c);
        a(b);

    }

    public static String b(byte a) {
//        a |= 256;
        String s = Integer.toBinaryString(a);
        if (a < 0) {
            s = s.substring(s.length() - 8);
        }
        System.out.println(s);
        return s;
    }

    public static void a(String str) {
        byte b = (byte) Integer.parseInt(str, 2);
        System.out.println(b);//127
    }

}
