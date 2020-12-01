package com.ot.jvm.day04.dispatch;

/**
 * 动态分派
 * @Title: DynamicDispatch
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/13
 * @Version: 1.0
 */
public class DynamicDisptch {

    static abstract class Human {
        abstract void sayhello();
    }

    static class Man extends Human {

        @Override
        void sayhello() {
            System.out.println("man");
        }

    }

    static class Woman extends Human {

        @Override
        void sayhello() {
            System.out.println("woman");
        }

    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayhello();     //   man
        woman.sayhello();   //  woman
        man = new Woman();
        man.sayhello();     //  woman
    }

}