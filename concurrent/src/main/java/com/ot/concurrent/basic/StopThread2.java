package com.ot.concurrent.basic;

public class StopThread2 {


    //    private static class MyThread1 extends Thread{
//        @Override
//        public void run() {
//            String name = Thread.currentThread().getName();
//            System.out.println(name+"is running");
//            System.o;
//        }
//    }
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            Thread currentThread = Thread.currentThread();
            System.out.println(currentThread.getName() + "isInterrupt :" + currentThread.isInterrupted());
            //通过判断中断标志来实现，与共享变量是一样的
            while (!currentThread.isInterrupted()) {
                System.out.println(currentThread.getName() + " is  running");
                System.out.println("is interrupt:" + currentThread.isInterrupted());
            }
            System.out.println(currentThread.getName() + " is stop ");
            System.out.println(" is interrupt :" + currentThread.isInterrupted());
        });
        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }
}
