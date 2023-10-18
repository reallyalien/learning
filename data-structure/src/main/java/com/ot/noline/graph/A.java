package com.ot.noline.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Title: A
 * @Author wangtao
 * @Date 2023/8/9 9:44
 * @description:
 */
public class A {

    public static void main(String[] args) {
        String point = "\\.";
        String a = "a.b";
        String[] split = a.split(point);
        System.out.println(split);


        System.out.println(System.currentTimeMillis());


        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add(1, "d");
        System.out.println(list);



        String c=" ?   %s   ?   %s   ?   %s ";
        String s = String.format(c, Arrays.asList("a", "c", "c"));
        System.out.println(s);
    }
}
