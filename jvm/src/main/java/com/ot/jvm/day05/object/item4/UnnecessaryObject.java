package com.ot.jvm.day05.object.item4;

/**
 * 避免创建不必要的对象
 */
public class UnnecessaryObject {
    public static void main(String[] args) {
        System.out.println("创建String对象");
        UnnecessaryObject.test01();
        System.out.println("=====================华丽的分割线==========================");
        System.out.println("直接使用字面量");
        UnnecessaryObject.test02();
    }

    public static void test01(){
        Long startTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < Integer.MAX_VALUE; i++) { // 2^32次方
            String str = new String("HelloWorld");
        }
        // 69 148 174 105 95 (单位:毫秒)
        System.out.println(System.currentTimeMillis() - startTimeMillis);
    }

    public static void test02(){
        Long startTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < Integer.MAX_VALUE; i++) { // 2^32次方
            String str = "HelloWorld";
        }
        // 3 4 4 2 10 (单位:毫秒)
        System.out.println(System.currentTimeMillis() - startTimeMillis);
    }
}
