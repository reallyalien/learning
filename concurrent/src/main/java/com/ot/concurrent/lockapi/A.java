package com.ot.concurrent.lockapi;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class A {

    public static void main(String[] args) throws ParseException {
//        String str="90.99%";
//        NumberFormat format = NumberFormat.getPercentInstance();//返回当前默认语言环境的百分比格式
//        Number parse = format.parse(str);
        double result1 = 0.51111122111111;
        DecimalFormat df = new DecimalFormat("0.00%");
        String r = df.format(result1);
        System.out.println(r);//great
    }
}
