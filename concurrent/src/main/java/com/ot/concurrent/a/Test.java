package com.ot.concurrent.a;

public class Test {

    public static void main(String[] args) {
        method(new Printable() {
            @Override
            public void print(String s) {
                System.out.println(s);
            }
        });
        method((s)->{
            System.out.println(s);
        });
    }

    public static void method(Printable printable) {
        printable.print("java");
    }
}
