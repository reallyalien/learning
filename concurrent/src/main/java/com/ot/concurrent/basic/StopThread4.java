package com.ot.concurrent.basic;

public class StopThread4 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                System.out.println("初始中断状态：" + Thread.interrupted());
                try {
                    //捕获到中断异常之后，清除中断标志，值为false，再去sleep已经不会再次捕获到异常了，
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    System.out.println("捕获到中断异常：" + Thread.interrupted());
                }
            }
        }, "t1");

        t1.start();
        Thread.sleep(2000);

        t1.interrupt();
    }
}
