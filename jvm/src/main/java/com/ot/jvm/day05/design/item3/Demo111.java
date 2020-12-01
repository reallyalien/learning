package com.ot.jvm.day05.design.item3;

import java.math.BigDecimal;

public class Demo111 {

    public static void main(String[] args) {
        //一定要使用字符串去构建bigDecimal对象
        BigDecimal num1=new BigDecimal("2.0");
        BigDecimal num2=new BigDecimal("1.1");
        System.out.println(num1.subtract(num2));

        BigDecimal num3=new BigDecimal(2.0);
        BigDecimal num4=new BigDecimal(1.1);
        System.out.println(num3.subtract(num4));
    }
}
