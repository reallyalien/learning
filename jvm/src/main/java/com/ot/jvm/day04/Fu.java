package com.ot.jvm.day04;

public class Fu {
    static {
        System.out.println("父类的静方法");
    }
    {
        System.out.println("父类的代码块");
    }
    public Fu(){
        System.out.println("父类的构造方法");
    }

    public void eat(){
        System.out.println("父类的eat方法");
    }
}
