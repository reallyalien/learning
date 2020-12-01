package com.ot.concurrent.lockapi;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock是互斥排他锁，同一时间只能有1个线程去执行任务，其实实际上应该是写操作互斥，读操作共享
 */
public class ConditionDemo {

    private static final Lock lock = new ReentrantLock();
    private static final Condition conditionA = lock.newCondition();
    private static final Condition conditionB = lock.newCondition();
    private static final Condition conditionC = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(() -> {
            try {
                if (lock.tryLock(1, TimeUnit.SECONDS)) {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("A");
                        conditionB.signal();
                        conditionA.await();
                    }
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }, "A");
        Thread b = new Thread(() -> {
            try {
                if (lock.tryLock(1, TimeUnit.SECONDS)) {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("B");
                        conditionC.signal();
                        conditionB.await();
                    }
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }, "B");
        Thread c = new Thread(() -> {
            try {
                if (lock.tryLock(1, TimeUnit.SECONDS)) {
                    for (int i = 0; i < 10; i++) {
                        System.out.println("C");

                        conditionA.signal();
                        conditionC.await();
                    }
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }, "C");
        a.start();
        b.start();
        c.start();
        TimeUnit.SECONDS.sleep(2);
        a.interrupt();
        b.interrupt();
        c.interrupt();
    }
}
