package com.ot.concurrent.basic;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class OnlyMain {
    public static void main(String[] args) {
        //Java虚拟机线程系统的管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //不需要获取同步的monitor和synchronized信息，仅获取线程和堆栈信息
        ThreadInfo[] dumpAllThreads = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo info : dumpAllThreads) {
            System.out.println("[" + info.getThreadId() + "]\t"+info.getThreadName());
        }
    }
}
