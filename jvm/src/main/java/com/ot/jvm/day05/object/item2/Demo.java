package com.ot.jvm.day05.object.item2;



/**
 * 使用私有构造方法或枚类实现Singleton属性
 * @Title: Demo
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/21
 * @Version: 1.0
 */
public class Demo {
    public static void main(String[] args) {
        SingletonEnum.Singleton.doSomething();
        Singleton.instance.doSomething();
        Singleton.getInstance().doSomethingStatic();

    }
}
