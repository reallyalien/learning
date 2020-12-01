package com.ot.jvm.day04.deencrpt;

/**
 * @Title: DemoRun
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/13
 * @Version: 1.0
 */
public class DemoRun {

    public static void main(String[] args) throws Exception {
        //new出自定义类加载器
        CustomClassLoader demoCustomClassLoader = new CustomClassLoader("My ClassLoader");
        //设置加载类的路径
        demoCustomClassLoader.setBasePath("D:\\work\\ref-jvm\\bin\\");
        Class<?> clazz = demoCustomClassLoader.findClass("com.jvm.ch04.deencrpt.DemoUser");
        System.out.println(clazz.getClassLoader());
        Object o = clazz.newInstance();
        System.out.println(o);
    }
}
