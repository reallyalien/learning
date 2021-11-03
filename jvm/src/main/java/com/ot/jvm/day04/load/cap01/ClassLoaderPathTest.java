package com.ot.jvm.day04.load.cap01;

import sun.misc.Launcher;

import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderPathTest {
    public static void main(String[] args) {
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
        //除了当前项目的classpath也会加载pom当中的jar包
        URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        URL[] urLs2 = systemClassLoader.getURLs();
        for (URL url : urLs2) {
            System.out.println(url);
        }
    }
}
