package com.ot.jvm.day04.initialization;

/**
 * @Title: InitializaTest
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/13
 * @Version: 1.0
 */
public class InitializaTest {
    //  静态变量
    public static String staticFiled = "静态变量";

    //  变量
    public String filed = "变量";

    //  静态块
    static {
        System.out.println(staticFiled);
        System.out.println("静态块");
    }

    //  初始块
    {
        System.out.println(filed);
        System.out.println("初始块");
    }

    //  构造方法
    public InitializaTest() {
        System.out.println("构造方法");
    }

    public static void main(String[] args) {
        new InitializaTest();
    }
}
