package com.ot.sort;

import java.util.Arrays;

/**
 * 基数排序 空间换时间的经典算法 因为按顺序放，因此基数排序是稳定的，不支持负数，切记，如果非要使用负数，得取绝对值
 */
public class RadixSorting {

    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};
        int[] sort = sort(arr);
        System.out.println(Arrays.toString(sort));
    }

    public static int[] sort(int[] arr) {
        //定义一个二维数组
        int[][] bucket = new int[10][arr.length];
        //为了记录每个桶放了多少数据，定义一个一位数组
        int[] bucketElementNum = new int[10];
        //先得到数组当中最大数的位数
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        //得到最大数是几位数
        int maxlength = (max + "").length();

        //开始循环
        for (int x = 0, n = 1; x < maxlength; x++, n *= 10) {
            //针对每一轮轮排序
            for (int i = 0; i < arr.length; i++) {
                int digit = arr[i] / n % 10;
                bucket[digit][bucketElementNum[digit]] = arr[i];
                bucketElementNum[digit]++;
            }
            //按照桶的顺序依次取出元素,桶的个数是确定的
            int index = 0;
            for (int j = 0; j < 10; j++) {
                //先判断桶中元素的个数，不为0才取出
                if (bucketElementNum[j] != 0) {
                    for (int k = 0; k < bucketElementNum[j]; k++) {
                        arr[index] = bucket[j][k];
                        index++;
                    }
                }
                //每次结束之后将存放桶中个数清空
                bucketElementNum[j] = 0;
            }
        }
        return arr;
    }
}
