package com.ot.jvm.day04.dispatch;

/**
 * 静态分派
 */
public class StaticDispatch {
    static abstract class Human {

    }

    static class Man extends Human {

    }

    static class Woman extends Human {

    }

    public void sayhello(Human guy) {
        System.out.println("Human guy");

    }
    public void sayhello(Man guy) {
        System.out.println("Man guy");

    }

    public void sayhello(Woman guy) {
        System.out.println("Woman guy");
    }

    public static void main(String[] args) {
        Human man = new Man(); //   Human 静态类型（编译）    Man 实际类型（运行的时候）
        Human woman = new Woman();
        StaticDispatch staticDispatch = new StaticDispatch();
        staticDispatch.sayhello(man);// Human guy
        staticDispatch.sayhello(woman);// Human guy
    }
}