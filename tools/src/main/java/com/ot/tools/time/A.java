package com.ot.tools.time;

import java.util.Calendar;

public class A {
    public static void main(String[] args) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 0);
        int i = instance.get(Calendar.DAY_OF_WEEK);
        if (i == 1) {
            i = 7;
        } else {
            i -= 1;
        }
        System.out.println(i);
    }
}
