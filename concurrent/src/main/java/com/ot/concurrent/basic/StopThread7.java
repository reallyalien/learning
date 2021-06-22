package com.ot.concurrent.basic;

import java.util.concurrent.locks.LockSupport;

public class StopThread7 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {

            //线程的静态方法会返回中断状态，并且清除中断标志，所以要获取当前线程的中断状态别使用静态方法
            System.out.println("初始中断状态：" + Thread.currentThread().isInterrupted());
            System.out.println(1);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println(2);
                System.out.println("唤醒之后中断状态1：" + Thread.currentThread().isInterrupted());
                System.out.println("唤醒之后中断状态2：" + Thread.currentThread().isInterrupted());
                System.out.println("唤醒之后中断状态3：" + Thread.currentThread().isInterrupted());
                System.out.println("唤醒之后中断状态4：" + Thread.currentThread().isInterrupted());
                System.out.println(3);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException interruptedException) {
                    System.out.println("");
                    interruptedException.printStackTrace();
                }
                System.out.println("第二次sleep结束");
            }
        }, "t1");

        t1.start();
        Thread.sleep(2000);

        t1.interrupt();
    }
}
