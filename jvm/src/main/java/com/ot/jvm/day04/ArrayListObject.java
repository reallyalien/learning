package com.ot.jvm.day04;

import java.util.ArrayList;

public class ArrayListObject {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Object> objects = new ArrayList<Object>();
        for (int i = 0; i < 1000000; i++) {
            objects.add(new String("hello"));
        }
    }
}
