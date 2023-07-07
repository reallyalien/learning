package com.ot.concurrent.aqs.linkList;

import cn.hutool.core.util.ArrayUtil;

import java.math.BigDecimal;

/**
 * @Title: A
 * @Author wangtao
 * @Date 2023/6/30 10:28
 * @description:
 */
public class A {

    public static void main(String[] args) {
        Object[] objects = new Object[10];
        int length = ArrayUtil.length(objects);
        boolean allEmpty = ArrayUtil.isAllEmpty(objects);
        boolean allNull = ArrayUtil.isAllNull(objects);
        System.out.println(length);
        BigDecimal b1 = new BigDecimal("2");
        BigDecimal b2 = new BigDecimal("5");
        int i = b1.compareTo(b2);
        System.out.println(i);
    }
}
