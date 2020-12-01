package com.ot.concurrent.lockapi.readandwrite;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 写写排斥 ,两个线程不可以同时获取到锁进行写操作
 */
public class MyWriteTask {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void write() {
        try {
                lock.writeLock().lock();
                System.out.println(Thread.currentThread().getName() + " start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " end");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        MyWriteTask task = new MyWriteTask();
        Thread t1 = new Thread(() -> {
            task.write();
        }, "t1");
        Thread t2 = new Thread(() -> {
            task.write();
        }, "t2");
        t1.start();
        t2.start();
    }
}
