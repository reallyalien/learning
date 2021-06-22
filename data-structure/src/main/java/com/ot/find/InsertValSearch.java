package com.ot.find;


/**
 * 插值查找算法  数组分布均匀查找极快，
 */
public class InsertValSearch {
    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i;
        }

        int index = findIndex(arr, 0, arr.length - 1, 56);
        System.out.println(index);
    }

    public static int findIndex(int[] arr, int left, int right, int findVal) {
        //递归整个数组，没有找到,如果小于最小的，或者大于最大的直接退出
        if (left > right || findVal < arr[0] || findVal > arr[arr.length - 1]) return -1;
        int mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]);
        if (findVal > arr[mid]) {
            //向右递归
            return findIndex(arr, mid + 1, right, findVal);
        } else if (findVal < arr[mid]) {
            //向左递归
            return findIndex(arr, left, mid - 1, findVal);
        } else {
            return mid;
        }
    }

}
