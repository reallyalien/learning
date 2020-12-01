package com.ot.concurrent.basic;

public class A {

    public static void main(String[] args) {

        try {
            int a=1/0;
        }catch (Exception e){
//            throw new RuntimeException("1");
        }


        System.out.println("aaa");
    }
}
