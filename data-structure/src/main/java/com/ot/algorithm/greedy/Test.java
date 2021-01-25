package com.ot.algorithm.greedy;

import java.util.HashSet;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        Set<String> set1=new HashSet<>();
        set1.add("1");
        set1.add("2");
        set1.add("100");

        Set<String> set2=new HashSet<>();
        set2.add("1");
        set2.add("2");
        set2.add("200");

        //求交集，set1是交集的内容
        set1.retainAll(set2);
        System.out.println(set1);
    }
}
