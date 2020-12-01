package com.ot.concurrent.pool;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Executor-->ExecutorService-->AbstractExecutorService--)ThreadPoolExecutor
 * ThreadPoolExecutor线程池的核心类工具类new的线程池，底层就是ThreadPoolExecutor
 *
 */
public class WeiYunSuanDemo {

    private  final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;  //正常状态，接收新的任务，处理等待队列当中的任务
    private static final int SHUTDOWN = 0 << COUNT_BITS;  //不接受新的任务，但是会处理正在执行的任务和等待队列当中的任务
    private static final int STOP = 1 << COUNT_BITS;      //不接受新的任务，中断正在执行的任务，不再处理等待队列当中的任务
    private static final int TIDYING = 2 << COUNT_BITS;   //所有的任务都销毁了，wordCount=0，线程池的状态变成tidying的状态时， 整理
                                                            //会执行钩子方法terminated()
    private static final int TERMINATED = 3 << COUNT_BITS;//terminated()执行完毕，线程池的状态变成这个

    // Packing and unpacking ctl
    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }


    public static void main(String[] args) {
        System.out.println(WeiYunSuanDemo.CAPACITY);  //5 3687 0911
        System.out.println(Integer.toBinaryString(WeiYunSuanDemo.CAPACITY));//  1 1111 11111111 11111111 11111111
        System.out.println(Integer.toBinaryString((1 << 4) - 1));

        System.out.println(Integer.toBinaryString(WeiYunSuanDemo.RUNNING)); //      111   0 0000 0000 0000 0000 0000 0000 0000
        System.out.println(Integer.toBinaryString(WeiYunSuanDemo.SHUTDOWN));//      000
        System.out.println(Integer.toBinaryString(WeiYunSuanDemo.STOP));  //        010 0000 0000 0000 0000 0000 0000 0000
        System.out.println(Integer.toBinaryString(WeiYunSuanDemo.TIDYING)); //      100 0000 0000 0000 0000 0000 0000 0000
        System.out.println(Integer.toBinaryString(WeiYunSuanDemo.TERMINATED));//    110 0000 0000 0000 0000 0000 0000 0000


        System.out.println(Integer.toBinaryString(-WeiYunSuanDemo.CAPACITY));// 1110 0000 00000000 00000000 00000001

        System.out.println(Integer.toBinaryString(WeiYunSuanDemo.ctlOf(WeiYunSuanDemo.RUNNING,0)));

    }
    @Test
    public void test(){
        System.out.println();
    }
    //  if (rs >= SHUTDOWN &&
    //                ! (rs == SHUTDOWN && firstTask == null && ! workQueue.isEmpty()))   return false;
}
