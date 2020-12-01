package com.ot.spring.proxy.staticproxy.impl;

import com.ot.spring.proxy.staticproxy.Person;

/**
 * 静态代理需要持有被代理类的实例，调用其方法，在方法前后进行自己的逻辑，但是当被
 * 代理对象的方法改变时，代理类也需改变
 */
public class Father implements Person {

    //设置目标类
    private Person person;

    public Father() {
    }

    //初始化操作
    public Father(Person person) {
        this.person = person;
    }

    @Override
    public void findLove() {
        //定制自己的逻辑
        /**
         * 1.安全监控
         * 2.日志监控
         * 3.效率监控
         */
        System.out.println("父类定制方法开始");
        //调用目标类的方法
        person.findLove();
        System.out.println("父类定制方法结束");
    }



    public static void main(String[] args) {
        Father father = new Father(new Son());
        father.findLove();
    }
}
