package com.ot.algorithm;

/**
 * 二分查找非递归实现
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {1, 7, 9, 45, 90, 100, 201};
        System.out.println(search(arr, 201));
    }

    /**
     * @param arr
     * @param val
     * @return 返回对应下标
     */
    public static int search(int[] arr, int val) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] == val) {
                return mid;
            } else if (val < arr[mid]) {
                right = mid - 1;//向左边查找
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
