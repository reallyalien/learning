package com.ot.concurrent.lockapi;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock是互斥排他锁，同一时间只能有1个线程去执行任务，其实实际上应该是写操作互斥，读操作共享
 * await方法，在lock之后获取到锁，await去释放锁，挂起线程，一旦满足条件就唤醒，再次获取锁
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
                        System.out.print("A\t");
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
                        System.out.print("B\t");
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
                        System.out.print("C\t");

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
        Thread.sleep(100);
        b.start();
        Thread.sleep(100);
        c.start();
        TimeUnit.SECONDS.sleep(2);
        a.interrupt();
        b.interrupt();
        c.interrupt();
    }
}
