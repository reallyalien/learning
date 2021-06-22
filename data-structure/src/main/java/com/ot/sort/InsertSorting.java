package com.ot.sort;

import java.util.Arrays;

/**
 * 插入排序 将待排序的数分成有序列表和无序列表，通过将无序列表的数拿出来插入到有序列表之前
 */
public class InsertSorting {

    public static void main(String[] args) {
        int[] arr = {101, 34, 23, 9, 89, 8};
        int[] sort = sort1(arr);
        System.out.println(Arrays.toString(sort));
    }

    public static int[] sort(int[] arr) {

        int insertVal = 0;
        int insertIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            insertVal = arr[i];
            insertIndex = i - 1;

            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                //把前一个大的值赋值给后一个
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }
            //退出while循环的时候即insertVal >= arr[insertIndex],所以要插入到insertIndex之后，所要插入的位置为insertIndex+1
            //这里优化一下，如果insertIndex+1==i，其实就是while没有进入，没有必要复制
            if (insertIndex + 1 != i) {
                arr[insertIndex + 1] = insertVal;
            }
//            System.out.printf("第%d轮结束之后%s\n", i, Arrays.toString(arr));
        }
        return arr;
    }

    public static int[] sort1(int[] arr) {
        int val = 0;
        int index = 0;
        for (int i = 1; i < arr.length; i++) {
            val = arr[i];
            index = i - 1;
            while (index >= 0 && val < arr[index]) {
                arr[index + 1] = arr[index];
                index--;
            }
            //退出while循环时,val已经大于arr[index]，自然要插入到arr[index]的后一个
            arr[index+1] = val;
        }
        return arr;
    }
}
