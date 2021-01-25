package com.ot.sort;

import java.util.Arrays;

/**
 * 归并排序 分治
 */
public class MergeSorting {
    public static void main(String[] args) {
        int[] arr = {8, 4, 5, 7, 1, 3, 6, 2 };
        int[] temp = new int[arr.length];
        sort(arr, 0, arr.length - 1, temp);
//        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] arr, int left, int right, int[] temp) {
        if (left < right) {
            int middle = (left + right) / 2;//中间索引 奇数个的话middle指向中间的数，会被划分到左侧，偶数的话指向左侧的最后一个元素
            //向左递归分解
            sort(arr, left, middle, temp);
            //向右递归
            sort(arr, middle + 1, right, temp);
            //到合并时,进栈出栈left right一直再变化
            merge(arr, left, middle, right, temp);
        }
    }

    /**
     * @param arr
     * @param left   左边有序的初始索引
     * @param middle 中间索引
     * @param right  右边最右边的索引
     * @param temp
     */
    public static void merge(int[] arr, int left, int middle, int right, int[] temp) {
//        System.out.println("left:" + left + "\tright:" + right);
        int i, j, t = 0;//t指向temp数组的当前索引
        i = left;//左边有序序列的初始索引
        j = middle + 1;//右边有序序列的初始索引
        //先把左右2边的数组进行比较插入temp数组,直到左右两边的序列有一边处理完毕
        while (i <= middle && j <= right) {
            if (arr[i] <= arr[j]) {
                //如果左边的元素小于右边的元素,将左边的元素插入temp数组
                temp[t] = arr[i];
                //同时i++,t++
                i++;
            } else {
                temp[t] = arr[j];
                //同时i++,t++
                j++;
            }
            t++;
        }
        //将剩余的一边序列直接拷贝到temp当中
        while (i <= middle) {
            //说明左边的有序序列还有剩余元素，全部填充到temp
            temp[t] = arr[i];
            t++;
            i++;
        }
        while (j <= right) {
            temp[t] = arr[j];
            j++;
            t++;
        }
        //将temp的有效数据拷贝到原数组,并不是每次都拷贝所有的
        //最后一次拷贝left=0，right=7
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            arr[tempLeft] = temp[t];
            tempLeft += 1;
            t += 1;
        }
//        System.out.println(Arrays.toString(arr));
//        System.out.println();
    }
}
