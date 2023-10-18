package com.ot.tools.jdbc.oracle;

import java.util.ArrayList;

public class A {

    public static final String ANONYMOUS_TABLE_NAME = "schema.table";

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        for (int i = 0; i < list.size(); i++) {
            Integer j = list.get(i);
            if (j == 1) {
                list.add(i, 10);
            }
            if (j == 3) {
                list.add(i, 100);
            }
        }
        System.out.println(list);
    }
}
