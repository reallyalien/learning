package com.ot.sort;

public class A {

    /**
     * 异或操作，相同为0 ，不同为1
     * 任何数与0异或，还是原来本身
     * 任何数与1异或，原来的数取反 0变1 ，1变0
     *
     * @param args
     */
    public static void main(String[] args) {
        int x = 1, y = 2;
        x = x ^ y;//x当中1的位置就是原来的x与y不同的位数
        y = x ^ y;
        x = x ^ y;
        int a = 9, b = 78;
        System.out.println((a ^ b ^ a) == b);
        System.out.println(x + "," + y);
    }
}
