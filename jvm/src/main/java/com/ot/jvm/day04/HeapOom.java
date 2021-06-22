package com.ot.jvm.day04;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

//对于MAT这个专业的内存泄漏分析工具，大家应该都比较熟悉，用起来很方便，只需要打开Eclipse，
// 然后把jmap或者jvm dump出来的文件拖到Eclipse里面，就会自动分析，然后以页面的形式展示出来结果。
//
//在结果里面可以看到内存总体情况、泄漏嫌疑对象、以及所有对象的内存占用情况。这对我们找到内存溢出、
// 存泄漏的元凶帮助很大。
//
//但是对于生产环境，总会遇到这样的问题，dump出来的文件8g以上，比较大，自己的台式机或者笔记本的内存
// 根本不够用。况且Eclipse启动起来，就占1个G的内存。
//VM Args：-XX:+HeapDumpOnOutOfMemoryError -Xms20m -Xmx20m
public class HeapOom {

    //在方法当中，对象随着方法调用结束，但这种静态的集合，不在使用，一定要clear，避免产生内存泄露
    //数据库连接 io 等 必须手动关闭
    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        List<HeapOom> list = new ArrayList<>();
        list.clear();
        int n = 0;
        while (n < 100) {
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(new HeapOom());
            n++;
        }
    }
}
