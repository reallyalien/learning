package com.ot.concurrent.basic;


import java.util.concurrent.locks.LockSupport;

public class TestParkUnpark {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("线程1开始...");
            try {
                Thread.sleep(5000);//t1睡眠了一秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("park...");
            LockSupport.park();//t1线程一秒后暂停
            System.out.println("unpark");
        }, "t1");
        t1.start();

        Thread.sleep(10000);//主线程睡眠二秒
        LockSupport.unpark(t1);//二秒后由主线程恢复t1线程的运行
    }
}
