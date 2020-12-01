package com.ot.jvm.day04.load.cap01;

/**
 * @Title: NotInitialization
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/13
 * @Version: 1.0
 */
public class NotInitialization {
    public static void main(String[] args)
    {
    //    System.out.println(SubClass.value);
        /**
         * SSClass
         * SuperClass init!
         * SubClass init   为什么没有输出？
         * 123   final   123  0?
         */

     //   SubClass[] sca = new SubClass[10];//没有触发SubClass初始化
     //   System.out.println(sca.length);
     //   SubClass sub  = new SubClass();

        System.out.println(ConstClass.str);
    }
}

class ConstClass{
    static {
        System.out.println("ConstClass init!");
    }
    public static final String str = "fame";
}
