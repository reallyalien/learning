package com.ot.sort;

public class SortingSpeedTest {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        int[] array = new int[20000];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 10_0000);//生成(0,800000)的值
        }
        long start = System.currentTimeMillis();
        //冒泡
//        BubbleSorting.sort(array);
        //选择
        SelectSorting.sort(array);
        long end = System.currentTimeMillis();
        System.out.printf("耗时：%d秒", (end - start) / 1000);
    }
}
