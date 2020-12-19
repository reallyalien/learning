package com.ot.find;

import java.util.ArrayList;
import java.util.List;

/**
 * 二分查找
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {1, 8, 9, 109, 109, 109, 267, 645, 8};
//        int index = findIndex(arr, 0, arr.ngth - 1, -1);
        List<Integer> list = findIndex2(arr, 0, arr.length, 10);
        System.out.println(list);
    }

    public static int findIndex(int[] arr, int left, int right, int findVal) {
        //递归整个数组，没有找到
        if (left > right) return -1;
        int mid = (left + right) / 2;
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

    public static List<Integer> findIndex2(int[] arr, int left, int right, int findVal) {
        ArrayList<Integer> list = new ArrayList<>();
        int index = 0;
        //递归整个数组，没有找到
        if (left > right) return list;
        int mid = (left + right) / 2;
        if (findVal > arr[mid]) {
            //向右递归
            findIndex(arr, mid + 1, right, findVal);
        } else if (findVal < arr[mid]) {
            //向左递归
            findIndex(arr, left, mid - 1, findVal);
        } else {
            int temp = mid - 1;
            //向左扫描，发现有一个不一样就可以退出
            while (true) {
                if (temp < 0 || arr[temp] != findVal) {
                    break;
                }
                list.add(temp);
                temp--;
            }
            list.add(mid);
            temp = mid + 1;
            while (true) {
                if (temp >= right || arr[temp] != findVal) {
                    break;
                }
                list.add(temp);
                temp++;
            }
        }
        return list;
    }
}
