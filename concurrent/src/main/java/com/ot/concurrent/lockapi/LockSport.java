package com.ot.concurrent.lockapi;

import java.awt.font.TextHitInfo;
import java.util.concurrent.locks.LockSupport;

/**
 * unpark相当于生产许可
 * park相当于消耗许可，可以先生产，再消费
 * lockSupport在park当中被中断，不会抛出异常，当然也不会清除中断标志
 *
 * 每次park都会将_counter置为0，如果之前为1（先unpark后park），则直接返回。后面的park调用就会阻塞。
 * unpark不会累计许可，最多为1
 */
public class LockSport {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("进入线程");
            LockSupport.park(null);
            System.out.println("结束线程");
            System.out.println("是否中断:" + Thread.currentThread().isInterrupted());
            LockSupport.park();
            //这里线程已经设置了中断标志位，所以直接返回，不会阻塞
            //LockSupport.park()会检查线程是否设置了中断标志位，如果设置了，则返回（这里并不会清除中断标志位）
            System.out.println("finally");
            System.out.println("是否中断:" + Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();
        Thread.sleep(2000);
//        LockSupport.unpark(t1);
        t1.interrupt();
    }
}
