package com.ot.jvm.day04;

public class Zi extends Fu {
    static {
        System.out.println("子类的静方法");
    }
    {
        System.out.println("子类的代码块");
    }
    public Zi(){
        System.out.println("子类的构造方法");
    }


    public void sleep(){
        System.out.println("子类的sleep方法");
    }

    public static void main(String[] args) {
        Zi zi = new Zi();
        zi.sleep();
        zi.eat();
    }
}
