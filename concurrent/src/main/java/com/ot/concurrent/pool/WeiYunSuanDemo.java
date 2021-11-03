package com.ot.concurrent.pool;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Executor-->ExecutorService-->AbstractExecutorService--)ThreadPoolExecutor
 * ThreadPoolExecutor线程池的核心类工具类new的线程池，底层就是ThreadPoolExecutor
 */
public class WeiYunSuanDemo {

    //线程池维护了一个原子类型的int变量 ctl，通过这一个字段来表示线程池当前的活动线程数和线程池的运行状态，其中低29位表示线程数，高3位表示
    //线程的运行状态
    /*
    线程池的运行状态有RUNNING、SHUTDOWN、STOP、TIDYING、TERMINATED。状态所对应的数字其实没有什么意义，重点需要了解状态代表的含义。

RUNNING：该状态下的线程池可以接受新任务，并且可以处理等待队列中的任务。
SHUTDOWN：该状态下的线程池不再接受新任务，但是可以处理等待队列中的任务。
STOP：该状态下的线程池不再接受新任务，不再处理等待队列中的任务，会中断正在执行的任务。
TIDYING：所有的任务都已经中止，活动线程数为0，此状态下的线程池即将转移到TERMINATED状态。
TERMINATED：terminated()执行完后到达此状态。
线程池的状态转移包括如下几个：

RUNNING -> SHUTDOWN，在执行shutdown()方法时，线程池经历了这种状态转移过程。
RUNNING -> STOP或者SHUTDOWN -> STOP，在执行shutdownNow()方法时，线程池经历了这种状态转移过程。
SHUTDOWN -> TIDYING，当等待队列和池中的任务都为空时，经历了这种状态转移过程。
STOP -> TIDYING，池中任务为空时，经历这种状态转移过程。
TIDYING -> TERMINATED，执行terminated()方法时经历这个状态转移过程。
     */


    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3; //29 活动线程占用的位数
    private static final int CAPACITY = (1 << COUNT_BITS) - 1; //活动线程的最大数量

    //线程的5种运行状态保留在高3位
    //线程池5种运行状态，保存在ctl高3位
    //11111111 11111111 11111111 11111111左移29位后只保留高位3个1即：
    //11100000 00000000 00000000 00000000
    private static final int RUNNING = -1 << COUNT_BITS;
    //0左移29位后
    //00000000 00000000 00000000 00000000
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    //1左移29位后
    //00100000 00000000 00000000 00000000
    private static final int STOP = 1 << COUNT_BITS;
    //2左移29位后
    //01000000 00000000 00000000 00000000
    private static final int TIDYING = 2 << COUNT_BITS;
    //3左移29位后
    //01100000 00000000 00000000 00000000
    private static final int TERMINATED = 3 << COUNT_BITS;

    //=============================================================================================================================

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

        System.out.println(Integer.toBinaryString(WeiYunSuanDemo.ctlOf(WeiYunSuanDemo.RUNNING, 0)));

    }

    @Test
    public void test() {
        System.out.println();
    }
    //  if (rs >= SHUTDOWN &&
    //                ! (rs == SHUTDOWN && firstTask == null && ! workQueue.isEmpty()))   return false;


    /*
execute的执行逻辑其实前面已经提到了，这里根据代码再分析下：

1.对于空的任务，线程池会抛出NPE异常
2.通过workerCountOf方法获取线程池的线程数，若线程数小于核心线程数，创建一个核心线程并将任务作为该核心线程的第一个任务。
3.若创建线程失败，重新获取线程池状态。尝试将任务添加到等待队列，需要注意的是，任务添加到等待队列成功后，需要进一步检
  查线程池状态，因为这个过程线程池的状态可能已经改变。
4.尝试将任务添加到等待队列，添加失败拒绝执行任务。
workCountOf方法很简单，通过”与”运算取ctl的低29位的值。

     */

    /**
     * 线程池倾向于使用核心线程来处理任务，从任务的添加策略可以看出，先考虑创建核心线程处理，再考虑放到阻塞队列，再考虑创建非核心线程处理。以上都不行，则使用任务拒绝策略
     * 通过向阻塞队列取任务的不同操作，能确保线程的存活，take()保证核心线程不死，poll()保证非核心线程存活等待一定时间
     * 线程池不区分核心线程和非核心线程，线程池是期望达到corePoolSize的并发状态，并允许在不得已情况下超载，达到corePoolSize ～ maximumPoolSize 的并发状态
     * 线程池状态和线程数量用ctl表示，高三位为数量，低29位为当前线程池数量
     * 线程池对状态的检测非常苛刻，几乎在所有稍微耗时或影响下一步操作正确性的代码前都校验ctl
     */
    /**
     *   boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;
     *
     *
     *    if ((wc > maximumPoolSize || (timed && timedOut))
     *                 && (wc > 1 || workQueue.isEmpty())) {
     *                 if (compareAndDecrementWorkerCount(c))
     *                     return null;
     *                 continue;
     *             }
     *
     *             try {
     *                 Runnable r = timed ?
     *                     workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
     *                     workQueue.take();
     *                 if (r != null)
     *                     return r;
     *                 timedOut = true;
     *             } catch (InterruptedException retry) {
     *                 timedOut = false;
     *             }
     * 其实线程池当中并不区分哪个线程是核心线程数，哪个线程是非核心线程，并没有明显的标志来区分 allowCoreThreadTimeOut默认是false，也就是timed
     * 这个boolean只判断当前线程的线程数是否大于核心线程数，如果小于，下面获取任务自然是走wordQueue的take方法，此方法会阻塞，在没有任务达到之前
     * 自然下面判断r是否为null，必然返回true，也就是可以获取到任务进行返回，如果当前线程池的线程数大于核心线程数，timed为true，走wordQueue的poll方法
     * 在设置的keepAliev超时之后然后null第一次循环返回，下一次就会通过cas修改ctl,并返回null。线程获取获取的while循环获取不到任务就销毁掉
     *
     */
}
