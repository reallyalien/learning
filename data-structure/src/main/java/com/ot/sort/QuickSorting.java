package com.ot.sort;

import java.util.Arrays;

/**
 * 快速排序，冒泡排序的改进版，将数组分组，将一个大问题转变成多个小问题然后递归求解，然后得出结论
 */
public class QuickSorting {
    public static void main(String[] args) {
        int[] arr = {-9, 78, 0, 23, -567, 70, 900, -1, 8901};
        int[] sort = sort1(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(sort));
    }

    /**
     * @param arr   数组
     * @param left
     * @param right
     * @return
     */
    public static int[] sort1(int[] arr, int left, int right) {
        if (left < right) {
            int l, r, x;
            l = left;
            r = right;
            x = arr[l];
            while (l < r) {
                //从右往左找第一个比x小的数
                while (l < r && x < arr[r]) {
                    r--;
                }
                //如果没有找到此时l>=r，因此下面的if如果可以进去，必然已经找到
                if (l < r) {
                    //将找到的数写入到l，并将l后移
                    arr[l] = arr[r];
                    l++;
                }
                //从左往右找比x大的数
                while (l < r && x > arr[l]) {
                    l++;
                }
                if (l < r) {
                    arr[r] = arr[l];
                    r--;
                }
            }
            arr[l] = x;
            //已l作为分界点，递归处理左边和右边
            sort1(arr, left, l - 1);
            sort1(arr, r + 1, right);
        }
        return arr;
    }
}
