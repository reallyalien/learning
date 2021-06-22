package com.ot.sort;

import java.util.Arrays;

/**
 * 一共进行数组大小-1次大排序
 * 每一趟排序的次数在减少
 * 如果我门发现在某趟排序当，没有发生一次交换，则可以提前结束排序
 */
public class BubbleSorting {

    public static void main(String[] args) {
        int[] arr = {3, 9, -1, 10, -2};
        int[] sort = sort1(arr);
        System.out.println("最后的结果：" + Arrays.toString(sort));
    }


    /**
     * 时间复杂度 O(n^2)
     *
     * @param arr
     * @return
     */
    public static int[] sort(int[] arr) {
        //第一趟排序将最大的数排列到最后
        int length = arr.length;
        int temp = 0;
        //是否进行过交换
        boolean flag = false;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
//            System.out.printf("第%d次排序结束的数组：%s\n", (i + 1), Arrays.toString(arr));
            if (!flag) {
                //在一趟排序当中，没有发生过排序，提前结束
                break;
            } else {
                //重置flag，进行下一次的排序
                flag = false;
            }
        }
        return arr;
    }

    public static int[] sort1(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    arr[j] = arr[j] ^ arr[j + 1];
                    arr[j + 1] = arr[j] ^ arr[j + 1];
                    arr[j] = arr[j] ^ arr[j + 1];
                }
            }
        }
        return arr;
    }
}
