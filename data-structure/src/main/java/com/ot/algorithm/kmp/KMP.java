package com.ot.algorithm.kmp;

import java.util.Arrays;

/**
 * kmp算法
 * .获取部分匹配表(PMT) ,当中的值是字符串的前缀集合和后缀集合的交集当中最长元素的长度
 * 主字符串在第i位匹配失败，也就是主字符串的(i-j)到(i-1)这一段与模式字符串的第0位到 PMT[j-1]是完全匹配的
 * 为什么是j-1，是因为在第j位已经匹配不上了，因此要向前一位看看之前
 */
public class KMP {

    public static void main(String[] args) {
        // 10 16 6
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int[] as = kmpNext(str2);
        System.out.println(Arrays.toString(as));
        int index = match(str1, str2, as);
//        System.out.println(index);

    }

    /**
     * 获取字串的部分匹配值,需要先获取部分匹配表
     * 求解部分匹配表的过程相当于 以模式字符串位主字符串，以模式字符串的前缀为目标字符串，一旦字符串匹配成功，那么当前next的值就是匹配
     * 成功的的字符串的长度
     */
    public static int[] kmpNext(String dest) {
        //保存部分匹配值
        int[] next = new int[dest.length()];
        next[0] = 0;//如果字符串只有1位，部分匹配值第一位始终为0
        //这里j有2层含义，一是下标，二是部分匹配表的值,即匹配字符串的长度
        for (int i = 1, j = 0; i < dest.length(); i++) {
            //这是kmp算法的基础,j>0说明之前有匹配到，所以才要获取相等的时候，否则也就没必要进来这个while，
            //只要不相等，就一直向前寻找相等的时候
            while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
                //也就是第i位与第j位不匹配了，也就是
                j = next[j - 1];
            }
            //这个if条件，必然会先于上面的while语句执行
            if (dest.charAt(i) == dest.charAt(j)) {//满足时，部分匹配值+1
                j++;
            }
            //不管匹配不匹配上，这里都得设置，匹配不上就是之前的数
            next[i] = j;
        }
        return next;
    }

    public static int match(String str1, String str2, int[] next) {
        for (int i = 0, j = 0; i < str1.length(); i++) {
            //需要考虑不相等的情况，kmp算法的核心点,去调整j的大小
            while (j > 0 && str1.charAt(i) != str2.charAt(j)) {
                j = next[j - 1];
            }
            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }
            //已经可以匹配到了
            if (j == str2.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }
}
