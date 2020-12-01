package com.ot.tomcat.myTomcat;

/**
 * B类继承了A，重写了抽象方法，
 */
public class B extends A {

//    public void a(){
//        aa();
//        System.out.println("AAA");
//    }
    @Override
    public void aa() {
        System.out.println("BBB");
    }

    public static void main(String[] args) {
        B b = new B();
        b.a();
    }
}
