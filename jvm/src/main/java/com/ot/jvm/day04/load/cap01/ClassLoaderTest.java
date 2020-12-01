package com.ot.jvm.day04.load.cap01;

import sun.misc.Launcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 说明类加载器的唯一性
 * 这里定义了一个classloader去实现classloader，
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        MyLoader myLoader = new MyLoader();
        //自定义的类加载器去加载当前类
        Object obj = myLoader.loadClass("com.ot.jvm.day04.load.cap01.ClassLoaderTest").newInstance();
        System.out.println("obj的类加载器" + obj.getClass().getClassLoader());
        System.out.println("obj的父类加载器" + obj.getClass().getClassLoader().getParent());
        System.out.println("obj的父父类加载器" + obj.getClass().getClassLoader().getParent().getParent());
        System.out.println("obj的父父父类加载器" + obj.getClass().getClassLoader().getParent().getParent().getParent());
        System.out.println("-----------------------------------");
        //这里创建的类，使用的类加载器是系统类加载器
        Object obj1 = new ClassLoaderTest();
        System.out.println("obj1的类加载器" + obj1.getClass().getClassLoader());
        System.out.println("obj1的父类加载器" + obj1.getClass().getClassLoader().getParent());
        System.out.println("obj1的父父类加载器" + obj1.getClass().getClassLoader().getParent().getParent());
        //  根类加载
        //  jvm默认：AppClassLoader  系统类加载器
//        System.out.println("系统默认的应用类加载器"+ClassLoader.getSystemClassLoader());
//        System.out.println("AppClassLoader的父类加载器："+
//                ClassLoader.getSystemClassLoader().getParent());
//        System.out.println("ExtClassLoader的父类加载器："+
//                ClassLoader.getSystemClassLoader().getParent().getParent());

        //obj虽说根obj1为同一个类对象，但是由不同的类加载器去加载，所以用instanceof去判断不是同一类对象，返回false
        System.out.println(obj instanceof ClassLoaderTest);


        System.out.println("bootstrap的加载路径");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL);
        }
        System.out.println("------------------------------------");

        System.out.println("扩展类加载器");
        URLClassLoader extClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader().getParent();
        System.out.println(extClassLoader);
        URL[] urLs1 = extClassLoader.getURLs();
        for (URL url : urLs1) {
            System.out.println(url);
        }
        System.out.println("--------------------------------------");

        System.out.println("系统类加载器");
        URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        URL[] urLs2 = systemClassLoader.getURLs();
        for (URL url : urLs2) {
            System.out.println(url);
        }
        System.out.println("自定义的类加载器");
//        URLClassLoader selfClassLoader= (URLClassLoader) obj.getClass().getClassLoader();
//        URL[] urLs3 = selfClassLoader.getURLs();
//        for (URL url : urLs3) {
//            System.out.println(url);
//        }
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader parent = ClassLoader.getSystemClassLoader().getParent().getParent();//null
        System.out.println(contextClassLoader);
        System.out.println(parent);

    }
}
