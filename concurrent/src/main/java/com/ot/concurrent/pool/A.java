package com.ot.concurrent.pool;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class A {

    public static void main(String[] args) {
        //核心线程数和最大线程数都是1，等待时间0s
        ExecutorService service = Executors.newFixedThreadPool(1);
        Runnable r = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread());
            }
        };
        for (int i = 0; i < 100; i++) {
            service.submit(r);
        }
    }
}
