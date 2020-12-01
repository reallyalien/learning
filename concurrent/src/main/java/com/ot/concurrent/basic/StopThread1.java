package com.ot.concurrent.basic;

public class StopThread1 {
    private static volatile boolean exit = false;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("线程启动");
            while (!exit) {

            }
            System.out.println("线程结束");
        }).start();

        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exit = true;
    }
}
