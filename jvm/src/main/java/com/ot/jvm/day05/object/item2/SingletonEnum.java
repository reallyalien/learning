package com.ot.jvm.day05.object.item2;

/**
 * 使用私有构造方法或枚类实现Singleton属性
 * @Title: SingletonEnum
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/21
 * @Version: 1.0
 */
public enum SingletonEnum {
    Singleton;
    public void doSomething() {
        System.out.println("实现单例的方法是声明单一枚举类");
    }
}
