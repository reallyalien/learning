package com.ot.tools.anno;

import java.math.BigDecimal;

public class B {

    public static void main(String[] args) {
        BigDecimal bigDecimal=new BigDecimal("15.00");
        System.out.println(bigDecimal);
        String s="REG13010100000000000052";
        String substring = s.substring(s.length() - 6);
        System.out.println(substring);
    }
}
