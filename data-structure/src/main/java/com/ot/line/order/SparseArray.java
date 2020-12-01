package com.ot.line.order;

/**
 * 稀疏数组
 * 1.第一行存储原来二维数组的行，列，以及不同的值的数量
 * 2.之后的每一行存储不同的值所在的行，列，值
 */

/**
 * 二维数组转稀疏数组
 * 1.遍历整个二维数组获取行，列，以及不同的值的个数num
 * 2.创建稀疏数组 int[num+1][3}
 * 3.依次填值
 * <p>
 * 稀疏数组转二维数组
 * 1.读取第一行获取整个二维数组的大小
 * 2.填值
 */
public class SparseArray {

    public static void main(String[] args) {
        //创建一个原始的二维数组
        //0:表示没有棋子，1；黑子 2；蓝子
        int[][] chessArr = new int[11][11];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;
        chessArr[3][4] = 1;
        //输出
        System.out.println("原始的二维数组：↓");
        for (int[] row : chessArr) {
            for (int i : row) {
                System.out.printf("%-3d", i);
            }
            System.out.println();
        }
        //遍历数组获取非0的值
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr[i][j] != 0) {
                    sum++;
                }
            }
        }
        //创建稀疏数组
        int[][] sparseArr = new int[sum + 1][3];
        //给稀疏数组赋值
        //第一行赋值
        sparseArr[0][0] = chessArr.length;
        sparseArr[0][1] = chessArr[0].length;
        sparseArr[0][2] = sum;
        //将非0值存放到稀疏数组当中
        int row = 1;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr[i][j] != 0) {
                    sparseArr[row][0] = i;
                    sparseArr[row][1] = j;
                    sparseArr[row][2] = chessArr[i][j];
                    row++;
                }
            }
        }
        //输出稀疏数组
        System.out.println("稀疏数组：↓");
        for (int[] ints : sparseArr) {
            for (int anInt : ints) {
                System.out.printf("%-3d", anInt);
            }
            System.out.println();
        }
        //稀疏数组转二维数组
        int[][] chessArr2 = new int[sparseArr[0][0]][sparseArr[0][1]];
        //从第二行开始
        for (int i = 1; i < sparseArr.length; i++) {
            int r = sparseArr[i][0];
            int c = sparseArr[i][1];
            int v = sparseArr[i][2];
            chessArr2[r][c] = v;
        }
        System.out.println("恢复的二维数组：↓");
        for (int[] row1 : chessArr2) {
            for (int i : row1) {
                System.out.printf("%-3d", i);
            }
            System.out.println();
        }
    }
}
