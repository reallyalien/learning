package com.ot.concurrent.basic;


/**
 * 与Object的wait &notify相比
 *
 * wait，notify和notifyAll必须配合Object Monitor一起使用，而unpark不必
 * park & unpark是以线程为单位来【阻塞】和【唤醒】线程，而notify只能随机唤醒一个等待线程，
 * notifyAll是唤醒所有等待线程，就不那么【精确】
 * park & unpark可以先unpark，而wait & notify不能先notify
 */

import java.util.concurrent.locks.LockSupport;

public class TestParkUnpark2 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("start...");
            try {
                Thread.sleep(10000);//t1睡眠了两秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("park...");
            LockSupport.park();//t1线程两秒后暂停
            System.out.println("resume...");
        }, "t1");
        t1.start();

        Thread.sleep(5000);//主线程睡眠一秒
        System.out.println("unpark...");
        LockSupport.unpark(t1);//一秒后由主线程恢复t1线程的运行
    }
}
