package com.ot.jvm.day05.design.item3;

import java.math.BigDecimal;

/**
 * @Title: CalculationDemo
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/22
 * @Version: 1.0
 */
public class CalculationDemo {

    public static void main(String[] args) {
//        double x = 0.01;
//        double y = 0.09;
//        System.out.println(x + y);  // 0.1

        float x = 0.01f;
        float y = 0.04f;
        System.out.println(x + y);

        //  0f/0d！=0  二进制里面 是一个无线接近0的数
        System.out.println(1/0d);

        System.out.println(2.0-1.1);

        //使用字符串构造BigDecimal对象   精确计数  BigDecimal  int
        BigDecimal a =new BigDecimal("2.0");
        BigDecimal b = new BigDecimal("1.1");
        System.out.println(a.subtract(b));

        //不使用字符串构造BigDecimal对象
        BigDecimal a1 =new BigDecimal(2.0);
        BigDecimal b1 = new BigDecimal(1.1);
        System.out.println(a1.subtract(b1));
//
//        System.out.println(Arithmetic4Double.sub(2.0, 1.1));
    }
}
