package com.ot.find;

/**
 * 线性查找
 */
public class OrderSearch {

    public static void main(String[] args) {
        int[] arr = {-1, 34, 89, 8};
        int index = findIndex(arr, 8);
        System.out.println(index);
    }

    public static int findIndex(int[] arr, int n) {
        //逐一比对
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == n) {
                return i;
            }
        }
        return -1;
    }
}
