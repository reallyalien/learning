package com.ot.jvm.day05.design.item1;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class EnumDemo {

    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName("com.ot.jvm.day05.design.item1.Test");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, Integer.class);
        constructor.setAccessible(true);
        Object o = constructor.newInstance("aaa",1);
        System.out.println(o);
    }
}
