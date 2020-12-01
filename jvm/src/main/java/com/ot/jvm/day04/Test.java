package com.ot.jvm.day04;

/**
 * @Title: Test
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/13
 * @Version: 1.0
 */
public class Test {

    static {
        i = 0;
   //     System.out.println(i);
    }
    static int i  = 1;

    public static void main(String[] args) {
        System.out.println(i); // 0   1
    }
}
