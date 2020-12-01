package com.ot.jvm.day01;

public class JavaStack {
    //  运行时常量池
    //常量
    final String fs = "有朝一日刀在手";
    //静态变量
    static String ss = "《XX霸道总裁爱上我》";
    //  静态常量
    static final String fss = "屠尽天下段章狗";

    //Fame老师出差
    int count = 0;//调用Fame方法次数的变量(实例变量)
    static int money = 10000;

    //  对象就在堆   new  命令  就会在堆分配空间
    private Object obj = new Object();

    //  虚拟机栈就是会所
    //  fame老师请Jack Ma做个SPA
    public void Fame() {
        //  局部变量的第一个我们的会所包厢
        //  SPA每人价格
        int j = 1000;  // 第二个
        //  花费1000套餐的SPA
        money = money - 1000;
        //  次数累加

        count++;  //  count= count + 1   不是单一的指令运行    非原子性操作   意味着非线程安全
        //  成员变量的引用和赋值
        Object o = obj;
        //  一边SPA，一边看小说
        System.out.println(ss);//静态变量的打印
        System.out.println(fs);//常量
        System.out.println(fss);
    }

    public void a() {
        count++;
        System.out.println(count);
        a();
    }

    public static void main(String[] args) throws Throwable {
        JavaStack javaStack = new JavaStack();
        javaStack.a();
    }
}