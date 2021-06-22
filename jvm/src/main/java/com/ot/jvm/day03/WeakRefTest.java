package com.ot.jvm.day03;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public class WeakRefTest {
    @Override
    protected void finalize() throws Throwable {
        //在回收对象之前调用此方法
        //但是在Java中很不幸，如果内存总是充足的，那么垃圾回收可能永远不会进行，
        // 也就是说filalize()可能永远不被执行，显然指望它做收尾工作是靠不住的。
        System.out.println("finalize运行了");
    }

    public static void main(String[] args) {
//        System.out.println(test());
//        System.out.println(test1());
        WeakRefTest weakRefTest = new WeakRefTest();
        WeakReference weakReference = new WeakReference(weakRefTest);
        weakRefTest = null;
        System.gc();
    }

    public static String test() {
        String a = new String("a");
        WeakReference<String> b = new WeakReference<String>(a);
        WeakHashMap<String, Integer> weakMap = new WeakHashMap<String, Integer>();
        weakMap.put(b.get(), 1);
        a = null;
        System.gc();
        String c = "";
        try {
            c = b.get().replace("a", "b");
            return c;
        } catch (Exception e) {
            c = "c";
            return c;
        } finally {
            c += "d";
            return c + "e";
        }
    }

    public static int test1() {
        int x = 1;
        try {
            x++;
            return x;
        } finally {
            ++x;
            return x;
        }
    }
}
