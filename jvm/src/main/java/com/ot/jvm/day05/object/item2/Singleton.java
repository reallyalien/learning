package com.ot.jvm.day05.object.item2;

import java.io.Serializable;

/**
 * 使用私有构造方法或枚类实现Singleton属性
 * @Title: Singleton01
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/21
 * @Version: 1.0
 */
public class Singleton implements Serializable {
    private transient static int count=0;
    //私有无参构造函数
    private Singleton() {
        synchronized (Singleton.class){
            if (count > 1){
                throw new RuntimeException("对象被创建2次");
            }
            count++;
        }
    }

    //单例对象
    public static final Singleton instance = new Singleton();


    //静态工厂方法
    private static final Singleton instance2 = new Singleton();
    public static Singleton getInstance() {
        return instance2;
    }

    public void doSomething() {
        System.out.println("私有无参构造函数");
    }

    public void doSomethingStatic() {
           System.out.println("静态工厂方法");
    }
}
