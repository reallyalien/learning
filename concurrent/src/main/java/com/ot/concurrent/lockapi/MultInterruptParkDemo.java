package com.ot.concurrent.lockapi;

import java.util.concurrent.locks.LockSupport;

public class MultInterruptParkDemo {

    public static volatile boolean flag = true;

    public static void main(String[] args) {
        ThreadDemo04 t4 = new ThreadDemo04();
        t4.start();
        t4.interrupt(); // 1. 底层调用unpark()方法，重置counter为1
        flag = false;
    }

    public static class ThreadDemo04 extends Thread {
        @Override
        public void run() {
            while (flag) {
            }
            try {
                //如果线程已中断，则清除中断标记并抛出中断异常，直接返回，但是并没有更改_counter。
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace(); // 2. 重置interrupt为false，LockSupport.park()将不会因为interrupt为true而放行！！
            }
            System.out.println("本打印出现在第一个sleep()之后");
            System.out.println(Thread.interrupted());
            System.out.println(Thread.interrupted());
            LockSupport.park(); // 3. counter为1，不发生阻塞；重置counter为0；
            System.out.println("本打印出现在第二个park()之后");
            LockSupport.park(); // 4. interrupt为false，并且counter为0；阻塞在这里！！
            System.out.println("本打印出现在第三个park()之后");
        }
    }
}
