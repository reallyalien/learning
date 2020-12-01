package com.ot.jvm.day04.load.cap01;

/**
 * @Title: SuperClass
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/13
 * @Version: 1.0
 */
public class SuperClass extends SSClass{

    static
    {
        System.out.println("SuperClass init!");
    }
    public static int value = 123;

    public SuperClass()
    {
        System.out.println("init SuperClass");
    }
}
