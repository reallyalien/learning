package com.ot.zookeeper;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class A {
    private static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(2, 0.75f);

    public static void main(String[] args) throws InterruptedException {
        map.put(5, 55);

        new Thread("Thread1-Name") {
            public void run() {
                System.out.println("Thread1-Name Start");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.put(7, 77);//断点位置 1
                System.out.println(map);
            }

        }.start();
        new Thread("Thread2-Name") {

            public void run() {
                try {
                    System.out.println("Thread2-Name Start");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.put(3, 33);// 断点位置2
                System.out.println(map);
            }

        }.start();


        // 断点位置 3
        System.out.println("Thread-Main-Name Start");
        System.out.println("Thread-Main-Name Start");
        System.out.println("Thread-Main-Name Start");


        Thread.sleep(500000);

    }
}
