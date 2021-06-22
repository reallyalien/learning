package com.ot.concurrent.lockapi;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;


public class LockInterrupt2 {

    private static final Lock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("线程一正在执行，将进入等待状态，当前时间= "+System.currentTimeMillis()+", 此时的中断标志位："+Thread.currentThread().isInterrupted());
            LockSupport.park();
            System.out.println("线程一从等待状态中醒来，当前时间= "+System.currentTimeMillis()+", 此时的中断标志位：" + Thread.currentThread().isInterrupted());
            LockSupport.park();
            System.out.println("线程一从等待状态中醒来，当前时间= "+System.currentTimeMillis()+", 此时的中断标志位："+Thread.currentThread().isInterrupted());
        });

        System.out.println("主线程正在执行");
        thread.start();
        System.out.println("主线程等待，睡眠两秒");
        TimeUnit.SECONDS.sleep(2);
        thread.interrupt();
    }

}
