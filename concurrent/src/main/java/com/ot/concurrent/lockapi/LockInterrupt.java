package com.ot.concurrent.lockapi;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * trylock(time,Timeunit)可以捕获中断
 */
public class LockInterrupt {

    private static final Lock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true){
                try {
//                    lock.tryLock(5, TimeUnit.SECONDS);
                    lock.lock();
                }catch (Exception e){
                    System.out.println("捕获到中断异常");
                }finally {
                    lock.unlock();
                }
            }
        });
        t.start();
        Thread.sleep(2_000);
//        t.interrupt();
    }
}
