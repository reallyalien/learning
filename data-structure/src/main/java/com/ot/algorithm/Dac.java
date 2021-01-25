package com.ot.algorithm;

/**
 * 分治算法 汉诺塔问题
 */
public class Dac {

    private static int count = 0;

    public static void main(String[] args) {
        resolve(3, 'a', 'b', 'c');
        //64次 ：1844 6744 0737 0955 1615
        System.out.println(count);
    }

    public static void resolve(int n, char a, char b, char c) {
        count++;
        //如果只有1个盘
        if (n == 1) {
//            System.out.println("第1个盘从" + a + "->" + c);
        } else {
            //总是看作2个盘
            //把最上面的盘移动到b，中间借组c
            resolve(n - 1, a, c, b);
            //把最下面的盘移动到c
//            System.out.println("第" + n + "个盘从" + a + "->" + c);
            //把b所有的盘移动到c,中间借组a
            resolve(n - 1, b, a, c);
        }
    }
}
