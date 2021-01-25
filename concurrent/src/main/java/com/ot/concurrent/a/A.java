package com.ot.concurrent.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("1111111");
        String s = sb.toString();
        sb.append("22222222");
        System.out.println(sb);
        System.out.println(s);
    }
}
