package com.ot.concurrent.basic;

import java.util.concurrent.locks.LockSupport;

public class StopThread5 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {

            System.out.println("初始中断状态：" + Thread.interrupted());
            System.out.println(1);
            LockSupport.park();
            System.out.println(2);
            System.out.println("唤醒之后中断状态1：" + Thread.interrupted());
            System.out.println("唤醒之后中断状态2：" + Thread.interrupted());
            System.out.println("唤醒之后中断状态3：" + Thread.interrupted());
            System.out.println("唤醒之后中断状态4：" + Thread.interrupted());
            System.out.println(3);

        }, "t1");

        t1.start();
        Thread.sleep(2000);

        t1.interrupt();
    }
}
