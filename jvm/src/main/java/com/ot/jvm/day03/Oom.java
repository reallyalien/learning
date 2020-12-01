package com.ot.jvm.day03;//package com.jd.jvm.day03;
//
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * 堆内存溢出
// * VM Args：-Xms30m -Xmx30m  -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
// * @Title: Oom
// * @Description: TODO
// * @Author: 六星教育     Fame老师
// * @CreateDate: 2020/4/10
// * @Version: 1.0
// */
//public class Oom {
//
//    public static void main(String[] args) {
//        List<Object> list = new LinkedList<>(); //在方法执行的过程中，它是GCRoots
//        int i =0;
//        while(true){
//            i++;
//            if(i%10000==0) System.out.println("i="+i);
//            list.add(new Object());
//        }
//
//        //String[] strings = new String[100000000];
//
//    }
//
//}
