package com.ot.jvm.day05.design.item3;

import java.math.BigDecimal;

public class TestBigDecimal {


    public static void main(String[] args) {
        /**
         * 传入字符串和静态方法是一样的,new 的构造方法不可传入double
         */
        BigDecimal b1 = new BigDecimal("0.01");
        BigDecimal b2 = new BigDecimal("0.09");
        System.out.println(b1.add(b2));

        //========================================
        BigDecimal b3 = new BigDecimal(0.01);
        BigDecimal b4 = new BigDecimal(0.09);
        System.out.println(b3.add(b4));

        //=======================================

        BigDecimal b5 = BigDecimal.valueOf(0.01);
        BigDecimal b6 = BigDecimal.valueOf(0.09);
        System.out.println(b5.add(b6));

    }
}
