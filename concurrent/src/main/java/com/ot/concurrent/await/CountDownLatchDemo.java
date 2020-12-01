package com.ot.concurrent.await;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/*
                          |
         ---------------->|
         ---------------->|
                          |
                          多个线程到了屏障之后await，等所有的线程到了之后state的值减为0，然后一起通过
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch main = new CountDownLatch(1);
        CountDownLatch run = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    System.out.println("运动员" + Thread.currentThread().getName() + "等待信号枪");
                    try {
                        main.await();
                        System.out.println("运动员" + Thread.currentThread().getName() + "开跑");
                        TimeUnit.SECONDS.sleep(3);
                        System.out.println("运动员" + Thread.currentThread().getName() + "到达终点");
                        run.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.submit(task);
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println("裁判" + Thread.currentThread().getName() + "即将鸣枪");
        main.countDown();
        System.out.println("裁判" + Thread.currentThread().getName() + "已经鸣枪，等待运动员跑完");
        run.await();
        System.out.println("三个运动员都到达终点");
    }
}
