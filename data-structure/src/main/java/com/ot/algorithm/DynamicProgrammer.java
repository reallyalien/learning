package com.ot.algorithm;

import java.util.Arrays;

/**
 * 动态规划算法解决背包问题
 * 背包问题：给定一个指定重量额背包，给定指定的具有一定价值和重量的物品，如果获取价值最大的解决方案
 * 分为01背包和完全背包（物品可以重复使用），无限背包可以转化为01背包
 * <p>
 * <p>
 * 1.v[i][0]=v[0][j]=0 当前背包重量是0或者没有放任何物品
 * 2.w[i]>j,v[i][j]=v[i-1][j] 当新加入的物品的重量大于当前背包的容量，直接使用上一个单元格最大值
 * 3.w[i]<=j,v[i][j]=Max(v[i-1][j],v[i]+v[i-1][j-w[i]])   j-w[i] 装入i物品之后，剩余的空间的最大值
 */
public class DynamicProgrammer {

    public static void main(String[] args) {
        max();
    }

    public static int max() {
        int[] w = {1, 4, 3};//物品的重量
        int[] val = {1500, 3000, 2000};//物品的价值
        int m = 4;//背包的重量
        int n = val.length;//物品的个数
        int[][] v = new int[n + 1][m + 1];//背包填表的二维数组
        //为了记录放入背包商品的情况
        int[][] record = new int[n + 1][m + 1];
        //初始化
        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0;//第一列都是0
        }
        for (int i = 0; i < v[0].length; i++) {
            v[0][i] = 0;//第一行都是0
        }
        for (int i = 1; i < v.length; i++) {
            //每一行
            for (int j = 1; j < v[i].length; j++) {
                //每一列
                if (w[i - 1] > j) {
                    v[i][j] = v[i - 1][j];
                } else {
                    int last = v[i - 1][j];
                    int now = val[i - 1] + v[i - 1][j - w[i - 1]];
                    if (last < now) {
                        v[i][j] = now;
                        record[i][j] = 1;//记录这个情况，并不只是最后一次才进来
                    } else {
                        v[i][j] = last;
                    }
                }
            }
        }
        for (int i = 0; i < v.length; i++) {
            System.out.println(Arrays.toString(v[i]));
        }
        System.out.println();
        for (int i = 0; i < record.length; i++) {
            System.out.println(Arrays.toString(record[i]));
        }
        System.out.println();
        //逆序输出record
        int i = record.length - 1;//最后一行
        int j = record[0].length - 1;//最后一列
        while (i > 0 && j > 0) {
            //从最后往前遍历
            if (record[i][j] == 1) {
                System.out.printf("第%d个商品放入背包\n", i);
                //背包的总量-已经存放的重量
                j -= w[i-1];
            }
            i--;
        }
        return -1;
    }
}
