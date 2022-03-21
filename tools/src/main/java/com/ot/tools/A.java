package com.ot.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A {

    public static void main(String[] args) {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(10);
        ids.add(11);
        ids.add(91);
        ids.add(17);
        ids.add(18);
        ids.add(15);
        ids.add(31);
        ids.add(21);
        ids.add(12);
        ids.add(110);
        ids.add(110);
        ids.add(110);
        ids.add(110);
        ids.add(110);
        ids.add(110);
        ids.add(110);
        ids.add(110);
        ids.add(110);
        ids.add(110);
        ids.add(110);
        ids.add(110);
        System.out.println(ids.size());
        //10个一组进行更新,数据量太多，sql会异常
        List<Integer> temp = new ArrayList<>(10);
        for (Integer id : ids) {
            temp.add(id);
            if (10 == temp.size()) {
                System.out.println(temp);
                temp.clear();
            }
        }
        System.out.println(temp);
    }
}
