package com.ot.tomcat.bootstrap;

public class Cat {

    private int age;
    private String name;

    private void say(String str) {
        System.out.println(str);
    }
    public void say1(String str){
        System.out.println(str);
    }

    public Cat() {
    }

    public Cat(int age,String name) {
        this.age = age;
        this.name=name;
    }
}
