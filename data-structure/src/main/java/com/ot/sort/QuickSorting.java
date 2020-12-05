package com.ot.sort;

import java.util.Arrays;

/**
 * 快速排序，冒泡排序的改进版
 */
public class QuickSorting {
    public static void main(String[] args) {
        int[] arr = {-9, 78, 0, 23, -567, 70, 900, -1, 8901};
        int[] sort = sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(sort));
    }

    public static int[] sort(int[] arr, int left, int right) {
        if (left < right) {
            int l, r, x;
            l = left;
            r = right;
            x = arr[l];
            while (l < r) {
                //从右往左找第一个比x小的数
                while (l < r && arr[r] > x) {
                    r--;
                }
                if (l < r) {
                    //如果找到了，将右边的数写到arr[l],并将l后移
                    arr[l++] = arr[r];
                }
                while (l < r && arr[l] < x) {
                    l++;
                }
                if (l < r) {
                    //如果找到了，将左边的数写到右边，并将r--
                    arr[r--] = arr[l];
                }
            }
            //退出while循环的时候，此时l=r 填入x
            arr[l] = x;
            //递归调用,处理左边和右边的
            sort(arr, left, l - 1);
            sort(arr, r + 1, right);
        }
        return arr;
    }
}
