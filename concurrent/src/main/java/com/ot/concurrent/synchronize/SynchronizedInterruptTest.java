package com.ot.concurrent.synchronize;

/**
 * synchronized:不可被中断的意思是，在阻塞的时候不可被中断，在获取到锁的时候就可以被中断
 */

public class SynchronizedInterruptTest {

    public static Object o1 = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("t1 enter");
            synchronized (o1) {
                try {
                    System.out.println("start lock t1");
                    Thread.sleep(20000);
                    System.out.println("end lock t1");
                } catch (InterruptedException e) {
                    System.out.println("t1 interruptedException");
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            System.out.println("t2 enter");
            synchronized (o1) {
                try {
                    System.out.println("start lock t2");
                    Thread.sleep(1000);
                    System.out.println("end lock t2");
                } catch (InterruptedException e) {
                    System.out.println("t2 interruptedException");
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();

        t2.interrupt();//修改线程2的中断标志
        Thread.sleep(2);
    }
}
