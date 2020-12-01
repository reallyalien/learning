package com.ot.concurrent.basic;

public class StopThread3 {


    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            Thread currentThread = Thread.currentThread();
            System.out.println(currentThread.getName() + "刚开始 isInterrupt :" + currentThread.isInterrupted());
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("catch is interrupt:" + currentThread.isInterrupted());
                    currentThread.interrupt();
                    System.out.println("重新中断 is interrupt:" + currentThread.isInterrupted());
                    e.printStackTrace();
                    break;//捕获到异常之后去中断线程。捕获不到自然这个catch进不来，当然也就无法中断。
                }
                System.out.println(currentThread.getName() + " is  running");
                System.out.println("is interrupt:" + currentThread.isInterrupted());
            }
            System.out.println(currentThread.getName() + " is stop ");
            System.out.println(" is interrupt :" + currentThread.isInterrupted());
        });
        thread.start();
        Thread.sleep(3000);
        thread.interrupt();//主线程去打断子线程，设置中断标志为true，一旦子线程发生阻塞，调用sleep或者wait，则会捕获到interrupt异常
        //并且清除中断标志，我们可以捕获到异常之后再去手动调一下interrupt，并且将当前线程break。
    }
}
