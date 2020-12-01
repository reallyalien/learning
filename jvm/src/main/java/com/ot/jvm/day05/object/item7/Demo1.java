package com.ot.jvm.day05.object.item7;

public class Demo1 {

    public static void main(String[] args) {
        System.out.println(n());
    }

    public static int n(){
        try {
            return 1;
        }finally {
            return 2;
        }
    }
}

