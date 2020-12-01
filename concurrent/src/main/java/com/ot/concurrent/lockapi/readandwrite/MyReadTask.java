package com.ot.concurrent.lockapi.readandwrite;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读读共享 ,两个线程可以同时获取到锁进行读操作
 */
public class MyReadTask {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void read() {
        try {
            if (lock.readLock().tryLock()) {
                System.out.println(Thread.currentThread().getName() + " start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " end");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void main(String[] args) {
        MyReadTask myReadTask = new MyReadTask();
        Thread t1 = new Thread(() -> {
            myReadTask.read();
        }, "t1");
        Thread t2 = new Thread(() -> {
            myReadTask.read();
        }, "t2");
        t1.start();
        t2.start();
    }
}
