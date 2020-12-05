package com.ot.sort;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertSorting {

    public static void main(String[] args) {
        int[] arr = {101, 34, 23, 9, 89, 8};
        int[] sort = sort(arr);
        System.out.println(Arrays.toString(sort));
    }

    public static int[] sort(int[] arr) {

        int insertVal = 0;
        int insertIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            insertVal = arr[i];
            insertIndex = i - 1;

            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }
            //退出while循环的时候即insertVal >= arr[insertIndex],所以要插入到insertIndex之后，所要插入的位置为insertIndex
            //这里优化一下，如果insertIndex+1==i，没有必要复制
            if (insertIndex + 1 != i) {
                arr[insertIndex + 1] = insertVal;
            }
//            System.out.printf("第%d轮结束之后%s\n", i , Arrays.toString(arr));
        }
        return arr;
    }
}
