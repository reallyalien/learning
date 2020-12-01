package com.ot.concurrent.aqs;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class AqsDemo implements Lock {

    private final AtomicReference atomicReference = new AtomicReference();

    private final LinkedBlockingQueue<Thread> blockingQueue = new LinkedBlockingQueue<>();


    @Override
    public void lock() {
        Thread currentThread = Thread.currentThread();
        if (!atomicReference.compareAndSet(null, currentThread)) {
            blockingQueue.add(currentThread);
            LockSupport.park();//阻塞当前线程
            blockingQueue.remove(currentThread);//被唤醒之后，从阻塞队列当中删除
        }
    }

    @Override
    public void unlock() {
        Thread thread = Thread.currentThread();
        if (atomicReference.compareAndSet(thread,null)){
            //解锁成功
            LockSupport.unpark(thread);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }


}
