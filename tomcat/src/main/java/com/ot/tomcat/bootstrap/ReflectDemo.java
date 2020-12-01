package com.ot.tomcat.bootstrap;


import java.lang.reflect.Method;

public class ReflectDemo {

    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("com.ot.tomcat.bootstrap.Cat");
            Cat cat = (Cat) clazz.newInstance();
            Method method = clazz.getDeclaredMethod("say", String.class);
            method.setAccessible(true);
            method.invoke(cat,"aaa");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
