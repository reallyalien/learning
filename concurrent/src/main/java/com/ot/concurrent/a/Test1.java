package com.ot.concurrent.a;

public class Test1 {

    public static void main(String[] args) {
        method(new Calculate() {
            @Override
            public int cheng(int a, int b) {
                return a * b;
            }
        });
        method((o1, o2) -> o1 * o2);
        method(new Dog()::multi);
    }

    public static void method(Calculate c) {
        int cheng = c.cheng(10, 20);
        System.out.println(cheng);
    }
}
