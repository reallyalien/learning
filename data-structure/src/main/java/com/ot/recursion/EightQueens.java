package com.ot.recursion;

public class EightQueens {

    //定义一个max表示共有多少个皇后
    private int max = 8;
    //皇后结果的数组，下标即是行数，数值即是列数
    private int[] array = new int[max];

    private static int count = 0;

    public static void main(String[] args) {
        EightQueens queens = new EightQueens();
        queens.check(0);
        System.out.printf("共有%d种解法", count);
    }

    /**
     * 放置第n个皇后 0-7
     *
     * @param n
     * @return
     */
    public void check(int n) {
        if (n == max) {
            //n==8其实已经是第9个皇后，前面8个已经好了
            print();
            count++;
            return;
        }
        //依次放入皇后，并判断是否冲突,i其实就是列数，行数是n,比如说把第0个皇后放在第一行的第一列，然后找出所有可能的解法
        for (int i = 0; i < max; i++) {
            //先把当前皇后n 放在该行的第1列
            array[n] = i;
            //判断当前第n个皇后到第i列时，是否冲突
            if (!judge(n)) {
                //如果不冲突
                //接着放第n+1个皇后，开始递归,当n=7时，进入check，然后return，又开始for循环，将第n个皇后放在第二列看有没有冲突
                check(n + 1);
            }
            //如果冲突，继续执行for循环，放在本行的下一列
        }

    }

    /**
     * 查看第n个皇后是否与之前已经摆放的n-1个皇后冲突
     * 每个皇后必然在不同的行，所以这里的n也可以理解为行，
     *
     * @param n 第n个皇后
     */
    public boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            //同一列 或者 在同一斜线，（横纵距离相等，即在同一斜线，行-行 == 列-列）
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将皇后摆放的位置打印
     */
    public void print() {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "  ");
        }
        System.out.println();
    }
}
