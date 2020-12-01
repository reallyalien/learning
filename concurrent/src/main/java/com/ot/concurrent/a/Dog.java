package com.ot.concurrent.a;

public class Dog {

    public int multi(int a, int b) {
        int m = a * b;
        return m;
    }
    class B{
        public void a(){
            System.out.println(this);
        }

    }

    public static void main(String[] args) {
        B b = new Dog().new B();
        b.a();
    }
}
