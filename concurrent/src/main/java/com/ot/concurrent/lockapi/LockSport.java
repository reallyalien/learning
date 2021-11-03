package com.ot.concurrent.lockapi;

import java.util.concurrent.locks.LockSupport;

/**
 * unpark相当于生产许可
 * park相当于消耗许可，可以先生产，再消费
 * lockSupport在park当中被中断，不会抛出异常，当然也不会清除中断标志
 *
 * 每次park都会将_counter置为0，如果之前为1（先unpark后park），则直接返回。后面的park调用就会阻塞。
 * unpark不会累计许可，最多为1
 */
/**
 * 1) wait和notify方法必须和同步锁 synchronized一块儿使用。而park/unpark使用就比较灵活了，没有这个限制，可以在任何地方使用。
 *
 * 2) park/unpark 使用时没有先后顺序，都可以使线程不阻塞（前面代码已验证）。而wait必须在notify前先使用，如果先notify，再wait，则线程会一直等待。
 *
 * 3) notify只能随机释放一个线程，并不能指定某个特定线程，notifyAll是释放锁对象中的所有线程。而unpark方法可以唤醒指定的线程。
 *
 * 4)  调用wait方法会使当前线程释放锁资源，但使用的前提是必须已经获得了锁。而park不会释放锁资源。
 */

/**
 *  https://baijiahao.baidu.com/s?id=1666548481761194849&wfr=spider&for=pc
 */
public class LockSport {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("进入线程");
            LockSupport.park();
            System.out.println("结束线程");
            System.out.println("是否中断:" + Thread.currentThread().isInterrupted());
            LockSupport.park();
            //这里线程已经设置了中断标志位，也就是要中断它，所以直接返回，不会阻塞
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
