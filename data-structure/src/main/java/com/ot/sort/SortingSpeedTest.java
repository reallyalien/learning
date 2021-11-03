package com.ot.sort;

import java.util.Arrays;

public class SortingSpeedTest {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        //1000w数据在内存排序
        int[] array = new int[1000_0000];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 10000_0000);
        }
        long start = System.currentTimeMillis();
        //冒泡
//        BubbleSorting.sort(array); //58s
        //选择
//        SelectSorting.sort(array);//10s
        //插入
//        InsertSorting.sort(array);//2s
        //希尔交换排序
//        ShellSorting.exchangeSort(array);//
        //希尔移动排序
//         ShellSorting.moveSort1(array);//2s 这个太快了
        //快速排序
//        QuickSorting.sort1(array,0,array.length-1); //1s
        //归并排序
//        MergeSorting.sort(array, 0, array.length - 1, new int[array.length]);//0s
        //基数排序
        RadixSorting.sort(array);
        //堆排序
//        HeapSorting.sort2(array);
        //jdk
//        Arrays.sort(array); //1s 这个是真的快
        long end = System.currentTimeMillis();
        System.out.printf("耗时：%d秒", (end - start) / 1000);
    }
}
