package com.ot.sort;

import java.util.Arrays;

/**
 * 选择排序
 * <p>
 * 数组大小-1次排序
 * 每一轮又是一个循环
 * 每一轮找到最小的数放前面
 */
public class SelectSorting {

    public static void main(String[] args) {
        int[] arr = {3, 9, -1, 10, -2};
        System.out.printf("没排序之前的数组: %s\n", Arrays.toString(arr));
        sort1(arr);
        System.out.printf("排序之后的数组: %s\n", Arrays.toString(arr));
    }

    public static int[] sort(int[] arr) {
        int min = 0;
        int minIndex = 0;
        boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++) {
            min = arr[i];//假定当前这个数最小
            //这个循环去寻找最小数，找见之后反放在第i个位置
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {
                    flag = true;
                    min = arr[j];//重新指定min
                    minIndex = j;//指定当前最小数的下标
                }
            }
            if (flag) {
                //第一轮循环结束，交换第i个数和最小值
                arr[minIndex] = arr[i];
                arr[i] = min;
            } else {
                flag = false;
            }
//            System.out.printf("第%d次排序后的数组:%s\n", i + 1, Arrays.toString(arr));
        }
        return arr;
    }

    public static int[] sort1(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            //先指定当前数是最小的，并记录下标
            int min = arr[i];
            int index = i;
            for (int j = i + 1; j < length; j++) {
                if (arr[j] < min) {
                    min = arr[j];
                    index = j;
                }
            }
            //如果当前数不是最小的，则交换，否则不交换
            if (index != i) {
                arr[index] = arr[i];
                arr[i] = min;
            }
        }
        return arr;
    }
}
