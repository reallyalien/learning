package com.ot.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class AtomicDemo {
    //以原子类的方式更新，只能保证一个变量
    AtomicInteger count = new AtomicInteger(0);

    public void add() {
        count.incrementAndGet();
    }

    public int get() {
        return count.get();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicDemo demo = new AtomicDemo();
        Stream.of("t1", "t2", "t3", "t4").forEach(t -> {
            new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    demo.add();
                }
            }, t).start();
        });
        Thread.sleep(1000);
        System.out.println("多线程计算完成之后：" + demo.get());
    }
}
