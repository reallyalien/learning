package com.ot.concurrent.lockapi;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * trylock(time,Timeunit)可以捕获中断
 * lockInterruptibly可以相应中断
 * lock不可以响应中断
 * <p>
 * tryLock有3种方法退出
 * 1.指定时间内获取到锁
 * 2.超时为获取到锁
 * 3.在等待锁的时候被中断
 * <p>
 * lock与sync的不同
 * 在未获取到锁的时候可以响应中断也就是在未获取到锁的时候，有另外的线程可以打断它，但sync不行，只能在获取到锁的时候才可以打断
 *
 *   其ReentrantLock底层是使用LockSupport.park方法进行等待的，前面说了LockSupport.park是响应中断的，当线程进入ReentrantLock.lock
 *   方法里面进行阻塞后，此时调用Thread.interrupt()方法之后，该线程是会被中断被唤醒的，但是唤醒之后，会调用LockSupport.park再次
 *   进入等待状态，所以仅从宏观（表面）上面看ReentrantLock.lock是不支持响应中断的，从微观（原理）上面讲ReentrantLock.lock内部确
 *   实中断了响应，但是还是会被迫进行等待状态。
 */

/**
 * 函数流程如下：
 *
 * tryAcquire()尝试直接去获取资源，如果成功则直接返回（这里体现了非公平锁，每个线程获取锁时会尝试直接抢占加塞一次，而CLH队列中可能还有别的线程在等待）；
 * addWaiter()将该线程加入等待队列的尾部，并标记为独占模式；
 * acquireQueued()使线程阻塞在等待队列中获取资源，一直获取到资源后才返回。如果在整个等待过程中被中断过，则返回true，否则返回false。
 * 如果线程在等待过程中被中断过，它是不响应的。只是获取资源后才再进行自我中断selfInterrupt()，将中断补上。
 */
public class LockInterrupt {

    private static final Lock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
//                    lock.tryLock(5, TimeUnit.SECONDS);
                    lock.lock();
//                    lock.lockInterruptibly();
                    Thread.sleep(500000000);
                } catch (Exception e) {
                    System.out.println("t1捕获到中断异常");
                } finally {
                    lock.unlock();
                }
            }
        },"t1");
        Thread t2 = new Thread(() -> {
            while (true) {
                try {
//                    lock.tryLock(2, TimeUnit.SECONDS);
                    lock.lock();
                    Thread.sleep(500000000);
//                    lock.lockInterruptibly();
                } catch (Exception e) {
                    System.out.println("t2捕获到中断异常");
                } finally {
                    lock.unlock();
                }
            }
        },"t2");
//        Thread t3 = new Thread(() -> {
//            while (true) {
//                try {
////                    lock.tryLock(2, TimeUnit.SECONDS);
//                    lock.lock();
//                    Thread.sleep(500000000);
////                    lock.lockInterruptibly();
//                } catch (Exception e) {
//                    System.out.println("t3捕获到中断异常");
//                } finally {
//                    lock.unlock();
//                }
//            }
//        },"t3");
        t1.start();
        t2.start();
        Thread.sleep(1_00000000);
        t2.interrupt();
    }
}
