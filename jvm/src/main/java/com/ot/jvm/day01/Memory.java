package com.ot.jvm.day01;

public class Memory {
    //常量在编译期就已经确定了
    public static final int i;
    //变量不可以
    public static int j = 2;

    static {
        i = 7;
        System.out.println("静态方法执行了");
    }

    public static void main(String[] args) {

    }
}
