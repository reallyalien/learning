package com.ot.algorithm.kmp;


/**
 * 暴力匹配算法实现
 */
public class ViolenceMatch {

    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int match = match(str1, str2);
        System.out.println(match);
    }

    public static int match(String str1, String str2) {
        char[] c1 = str1.toCharArray();
        char[] c2 = str2.toCharArray();
        int s1 = c1.length;
        int s2 = c2.length;
        int i = 0;//指向c1
        int j = 0;//指向c2
        while (i < s1 && j < s2) {
            if (c1[i] == c2[j]) {
                i++;
                j++;
            } else {
                //i-已匹配到的然后在向后移动1位，i进行回溯
                i = i - j + 1;
                j = 0;
            }

        }
        //判断是否匹配成功
        if (j == s2) {
            System.out.println("匹配成功");
            //返回匹配开始的位置
            return i - j;
        }
        return -1;
    }
}
