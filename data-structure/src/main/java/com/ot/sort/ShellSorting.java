package com.ot.sort;

import java.util.Arrays;

/**
 * 希尔排序，也是一种插入排序，简单插入排序的升级版本，也被称为缩小增量排序，是将数据进行分组然后再直接插入排序
 */
public class ShellSorting {

    public static void main(String[] args) {
        int[] arr = {-100, 8, 9, 1, 7, 2, 3, 5, 4, 6, 0, 98, 788, 72, 89, -76};
        int[] ints = moveSort1(arr);
//        int[] ints = exchangeSort(arr);
        System.out.println(Arrays.toString(ints));
    }

    /**
     * 交换法
     *
     * @param arr
     * @return
     */
    public static int[] exchangeSort(int[] arr) {
        int temp = 0;
        //gap是步长，也是分组的个数
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //这里i从gap开始循环，
            for (int i = gap; i < arr.length; i++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
        }
        return arr;

//        //第一轮,将数据分为5组
//        for (int i = 5; i < arr.length; i++) {
//            for (int j = i - 5; j >= 0; j -= 5) {
//                if (arr[j] > arr[j + 5]) {
//                    temp = arr[j];
//                    arr[j] = arr[j + 5];
//                    arr[j + 5] = temp;
//                }
//            }
//        }
//        System.out.println(Arrays.toString(arr));
//        for (int i = 2; i < arr.length; i++) {
//            for (int j = i - 2; j >= 0; j -= 2) {
//                if (arr[j] > arr[j + 2]) {
//                    temp = arr[j];
//                    arr[j] = arr[j + 2];
//                    arr[j + 2] = temp;
//                }
//            }
//        }
//        System.out.println(Arrays.toString(arr));
//        for (int i = 1; i < arr.length; i++) {
//            for (int j = i - 1; j >= 0; j -= 1) {
//                if (arr[j] > arr[j + 1]) {
//                    temp = arr[j];
//                    arr[j] = arr[j + 1];
//                    arr[j + 1] = temp;
//                }
//            }
//        }
    }

    public static int[] moveSort(int[] arr) {
        int insertVal = 0;
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //这里i从gap开始循环，
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                insertVal = arr[j];
                while (j - gap >= 0 && arr[j - gap] > insertVal) {
                    //把前一个大的值赋值给后一个,
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                //当退出while循环的时候，arr[j-gap+gap]就是要插入的位置
                arr[j] = insertVal;
            }
        }
        return arr;
    }

    //希尔移动插入排序，缩小增量排序
    public static int[] moveSort1(int[] arr) {
        int val = 0;
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                //这里使用一个新的变量j，不要修改这里的i，否则会影响下一次循环
                int j = i;
                val = arr[j];
                while (j - gap >= 0 && val < arr[j - gap]) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j - gap + gap] = val;
            }
        }
        return arr;
    }
}
