package com.ot.concurrent.basic;


public class StopApi {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("正在执行：" + i);
                try {
                    if (i == 8) {
                        //1.调用stop 方法会立即打断线程，catch和finally的代码也无法执行，可能会出现一些数据丢失的情况，或者说文件关闭，释放资源的操作没办法执行
                        //2.调用stop方法，会立即释放当前线程的锁，会导致一些数据不一致的情况产生
                        Thread.currentThread().stop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("finally执行了" + i);
                }
            }
        }).start();

    }
}
