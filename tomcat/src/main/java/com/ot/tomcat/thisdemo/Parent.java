package com.ot.tomcat.thisdemo;

public class Parent {

    public void demo1(){
        System.out.println("1");
        //this是一个动态对象，编译时看父类，运行时看子类
        this.demo2();
        System.out.println(this);
    }

    public void demo2(){
        System.out.println("2");
    }

    public static void main(String[] args) {
        Parent parent = new Parent();
        parent.demo1();
    }
}
