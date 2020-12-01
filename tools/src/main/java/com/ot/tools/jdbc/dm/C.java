package com.ot.tools.jdbc.dm;

import java.util.Random;

public class C {
    public static void main(String[] args) {
        Random random = new Random();
        StringBuffer valSb = new StringBuffer();
        int length = 10;
        String charStr = "0123456789abcdefghijklmnopqrstuvwxyz";
        int charLength = charStr.length();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charLength);
            valSb.append(charStr.charAt(index));
        }
        System.out.println("随机数字字母："+valSb);
    }
}
