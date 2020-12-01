package com.ot.jvm.day01;

import java.lang.instrument.Instrumentation;

/**
 * 计算对象大小 instumentation，唯一的实现类构造方法私有，无法通过代码显示实例化，需要通过指定代理的方式，由jvm去实例化
 */
public class MyAgent {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst){
       instrumentation=inst;
    }
    public static long getObjectSize(Object o){
        if (instrumentation != null){
            return instrumentation.getObjectSize(o);
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("main is over");
    }

}
