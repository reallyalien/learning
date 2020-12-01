package com.ot.tomcat.thisdemo;

public class Child extends Parent{

    public void demo1(){
        super.demo1();
        System.out.println("3");
        this.demo2();
    }

    public void demo2(){
        System.out.println("4");
    }

    public static void main(String[] args) {
        Child child = new Child();
        child.demo1();
    }
}
