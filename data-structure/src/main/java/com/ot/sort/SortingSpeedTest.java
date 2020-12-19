package com.ot.sort;

public class SortingSpeedTest {

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        int[] array = new int[800_0000];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 1000_0000);//生成(0,800000)的值
        }
        long start = System.currentTimeMillis();
        //冒泡
//        BubbleSorting.sort(array); //58s
        //选择
//        SelectSorting.sort(array);//10s
        //插入
//        InsertSorting.sort(array);//2s
        //希尔交换排序
//        ShellSorting.exchangeSort(array);//26s
        //希尔移动排序
//         ShellSorting.moveSort(array);//0s 这个太快了
        //快速排序
//        QuickSorting.sort(array,0,array.length-1);
        //归并排序
//        MergeSorting.sort(array, 0, array.length - 1, new int[array.length]);//0s
        //基数排序
//        RadixSorting.sort(array);
        //堆排序
        HeapSorting.sort(array);
        long end = System.currentTimeMillis();
        System.out.printf("耗时：%d秒", (end - start) / 1000);
    }
}
