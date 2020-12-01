package com.ot.jvm.day03;//package com.jd.jvm.day03;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @Title: Finalizable
// * @Description: TODO
// * @Author: 六星教育     Fame老师
// * @CreateDate: 2020/4/9
// * @Version: 1.0
// */
//public class Finalizable {
//    static AtomicInteger aliveCount = new AtomicInteger(0);
//
//    Finalizable() {
//        aliveCount.incrementAndGet();
//    }
//
//    @Override
//    protected void finalize() throws Throwable {
//        Finalizable.aliveCount.decrementAndGet();
//    }
//
//    public static void main(String args[]) {
//        for (int i = 0;; i++) {
//            Finalizable f = new Finalizable();
//            if ((i % 100_000) == 0) {
//                System.out.format("After creating %d objects, %d are still alive.%n", new Object[] {i, Finalizable.aliveCount.get() });
//            }
//        }
//    }
//}
