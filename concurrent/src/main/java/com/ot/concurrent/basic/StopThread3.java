package com.ot.concurrent.basic;

public class StopThread3 {

    /**
     * 线程中断可以使一个线程从等待状态变为就绪状态，如果中断的线程处于运行状态，那么这个中断是不会有任何作用的
     * 想要一个线程从等待当中醒来，只有3种可能，1等待超时2被唤醒3使用中断
     *  注意：使用线程中断，并不是要把线程给终止或是杀死，而是让线程不再继续等待，而是让线程不再继续等待，
     *  线程可以继续往下执行代码，线程发生中断后，会抛出一个中断的异常，决定如何处理就看业务代码怎么写了。
     *
     *  中断原理：      Thread.interrupt()方法仅仅是在当前线程中打了一个停止的标识将中断标志修改为true，
     *  并没有真正的停止线程。如果在此基础上进入堵塞状态（sleep(),wait(),join()）,马上就会抛出一个InterruptedException，
     *  且中断标志被清除，重新设置为false。记住一点，当调用Thread.interrupt()，还没有进行中断时，此时的中断标志位是true，当发生中断之后（执行到sleep(),wait(),join()），这个时候的中断标志位就是false了。
     * @param args
     * @throws InterruptedException
     */

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            Thread currentThread = Thread.currentThread();
            System.out.println(currentThread.getName() + "刚开始 isInterrupt :" + currentThread.isInterrupted());

            System.out.println(currentThread.getName() + " is stop ");
            System.out.println(" is interrupt :" + currentThread.isInterrupted());
        });
        thread.start();
        Thread.sleep(3000);
//        thread.join();
        System.out.println("主线程----");
        thread.interrupt();//主线程去打断子线程，设置中断标志为true，一旦子线程发生阻塞，调用sleep或者wait，则会捕获到interrupt异常
        //并且清除中断标志，我们可以捕获到异常之后再去手动调一下interrupt，并且将当前线程break。
    }
}
