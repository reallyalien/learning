package com.ot.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/*
1. addAndGet(int delta) ：以原子方式将输入的数值与实例中原本的值相加，并返回最后的结果；
2. incrementAndGet() ：以原子的方式将实例中的原值进行加1操作，并返回最终相加后的结果；
3. getAndSet(int newValue)：将实例中的值更新为新值，并返回旧值；
4. getAndIncrement()：以原子的方式将实例中的原值加1，返回的是自增前的旧值
 */
public class AtomicIntegerApi {

    static AtomicInteger num = new AtomicInteger(100);

    public static void main(String[] args) throws InterruptedException {
//        System.out.println("num+1的值："+num.incrementAndGet());
//        System.out.println("num的值："+num.getAndIncrement());
//        System.out.println(num.get());
//        System.out.println(num.getAndSet(102));

        Thread thread1 = new Thread(() -> {
            num.compareAndSet(100, 105);//线程1更新完之后内存当中的值变为105
            System.out.println("1:" + num.get());
        });
        Thread thread2 = new Thread(() -> {
            try {
                thread1.join();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (!num.compareAndSet(104, 110)){
                continue;
            }


            //当期望值不是内存当中的值的时候，cas失败，
            // cas操作本身不支持自旋，需要借助外层for或者while循环
            //cas本身就是一次性的立马返回结果。
            System.out.println("2:" + num.get());
        });
        Thread thread3 = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            num.compareAndSet(105, 109);
            System.out.println("3:" + num.get());
        });
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
