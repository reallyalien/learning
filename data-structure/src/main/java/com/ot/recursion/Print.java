package com.ot.recursion;

public class Print {

    public static void main(String[] args) {
        long factorial = factorial(20);
        System.out.println(factorial);
    }

    public static void test(int n) {
        if (n > 2) {
            test(n - 1);
        }
        System.out.printf("n=%d\n", n);
    }

    public static long factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return factorial(n - 1) * n;
        }
    }
}
