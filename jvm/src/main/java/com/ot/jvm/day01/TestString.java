package com.ot.jvm.day01;

import java.util.ArrayList;
import java.util.List;

public class TestString {

    public static void main(String[] args) {
        String s1 = new String("abc");
        String s2 = "abc";
        System.out.println(s1==s2);
        String intern = s1.intern();
        System.out.println(s2 == intern);
    }
}
