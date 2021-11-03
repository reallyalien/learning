package com.ot.zookeeper.lock;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        DistributedLock lock1 = new DistributedLock();
        DistributedLock lock2 = new DistributedLock();

        Thread thread1 = new Thread(() -> {
            try {
                lock1.lock();
                System.out.println("线程1获取到锁");
                Thread.sleep(5000);
                lock1.unlock();
                System.out.println("线程1释放锁");
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        Thread thread2 = new Thread(() -> {
            try {
                lock2.lock();
                System.out.println("线程2获取到锁");
                Thread.sleep(5000);
                lock2.unlock();
                System.out.println("线程2释放锁");
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
    }
}
