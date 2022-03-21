package com.ot.concurrent;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class C {


    public static void main(String[] args) {
        List<People> list = new ArrayList<>();
        list.add(new People("10", "A"));
        list.add(new People("10", "B"));
        list.add(new People("10", "C"));
        list.stream().map(x -> {
            System.out.println(x);
            return x;
        });
//        for (People people : list) {
//            people.setAge("aaaaaaaaaaaaaaaaa");
//        }
//        System.out.println(list);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        System.out.println(hour + ":" + minute);
        System.out.println(hour + ":" + (minute + 5));


        BigDecimal a = new BigDecimal("2");
        BigDecimal b = new BigDecimal("1");
        if (a.compareTo(b) >= 0) {
            System.out.println("a>=b");
        } else {
            System.out.println("a<b");
        }

        String str = "1-2";
        String[] split = str.split("-");
        System.out.println(str);
    }
}
