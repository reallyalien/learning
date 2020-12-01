package com.ot.sort;

public class A {

    public static void main(String[] args) {
        String str="aaa*bbb";
        String s = str.replaceAll("\\*", "_");
        System.out.println(s);
    }
}
