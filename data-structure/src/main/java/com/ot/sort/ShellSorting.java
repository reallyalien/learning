package com.ot.sort;

import java.util.Arrays;

/**
 * 希尔排序，
 */
public class ShellSorting {

    public static void main(String[] args) {
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6};
        int[] ints = moveSort(arr);
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
                //这里的j第一次循环，j-gap肯定就小于0退出循环，比如当gap等于1时，当j=1，比较完arr[1]和arr[2]之后，j-1继续比较arr[0]和
                //arr[1],也就是j在循环过程当中会把它前面的数都得比较一遍
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
                    arr[j] = arr[j - gap];
                    j -=  gap;
                }
                //当退出while循环的时候，arr[j-gap+gap]就是要插入的位置
                arr[j] = insertVal;
            }
        }
        return arr;
    }
}
