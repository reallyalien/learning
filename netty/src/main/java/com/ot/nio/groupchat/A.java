package com.ot.nio.groupchat;

public class A {

    public static void main(String[] args) {
        System.out.println(a());
    }

    public static String a() {
        String a = "a";
        try {
            return a;
        } catch (Exception e) {

        } finally {
            a = "b";
            return a;
        }
    }
}
