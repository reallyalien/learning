package com.ot.zookeeper.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class A {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d日");
        String str = "2021年1月2日";
        Date parse = format.parse(str);
        String format1 = format.format(parse);
        System.out.println(format1);
    }
}
