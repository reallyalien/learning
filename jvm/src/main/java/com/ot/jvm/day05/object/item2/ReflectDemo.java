package com.ot.jvm.day05.object.item2;

import java.lang.reflect.InvocationTargetException;

/**
 * 反射：如果类的构造方法没有特别处理，则会创建多个对象。
 */
public class ReflectDemo {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Singleton singleton1 = Singleton.getInstance();
        Class clazz = Class.forName("com.ot.jvm.day05.object.item2.SingletonEnum");
//        Constructor constructor = clazz.getDeclaredConstructor();
//        constructor.setAccessible(true);
//        Singleton singleton2= (Singleton) constructor.newInstance();
//        System.out.println(singleton1.hashCode());
//        System.out.println(singleton2.hashCode());
        System.out.println(clazz);
//    }
}}
