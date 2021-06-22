package com.ot.concurrent.basic;

import java.util.concurrent.locks.LockSupport;

public class StopThread6 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {

            //线程的静态方法会返回中断状态，并且清除中断标志，所以要获取当前线程的中断状态别使用静态方法
            System.out.println("初始中断状态：" + Thread.currentThread().isInterrupted());
            System.out.println(1);
            //不会清除标志位，
            LockSupport.park();
            System.out.println(2);
            System.out.println("唤醒之后中断状态1：" + Thread.currentThread().isInterrupted());
            System.out.println("唤醒之后中断状态2：" + Thread.currentThread().isInterrupted());
            System.out.println("唤醒之后中断状态3：" + Thread.currentThread().isInterrupted());
            System.out.println("唤醒之后中断状态4：" + Thread.currentThread().isInterrupted());
            System.out.println(3);
            LockSupport.park();
            //这里不会再进行park了，
            System.out.println("第二次park");
        }, "t1");

        t1.start();
        Thread.sleep(2000);

        t1.interrupt();
    }
}
