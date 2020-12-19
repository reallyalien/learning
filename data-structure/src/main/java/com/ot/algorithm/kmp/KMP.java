package com.ot.algorithm.kmp;

import java.util.Arrays;

/**
 *  0  1  2  3   4  5  6  7  8
 *  A  B  A  B  C   A  B  A  A
 * [0, 0, 1, 2, 0, 1, 2, 3,  1]
 */

/**
 * kmp算法
 * .获取部分匹配表
 */
public class KMP {

    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int[] as = kmpNext("ABABCABAA");
        System.out.println(Arrays.toString(as));
//        int index = match(str1, str2, as);
//        System.out.println(index);

    }

    /**
     * 获取字串的部分匹配值
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
                j = next[j - 1];//j-1理解为减少一次匹配，
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
            if (j == str2.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }
}
