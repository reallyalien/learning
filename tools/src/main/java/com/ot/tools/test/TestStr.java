package com.ot.tools.test;

public class TestStr {

    public static void main(String[] args) {
        String a = "hello";
        String b = a;
        a="world";
        System.out.println(a);
        System.out.println(b);
        System.out.println(a == b);
    }
}
