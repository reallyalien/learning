package com.ot.algorithm.horse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 马踏棋盘 骑士周游问题
 * 图深度搜索
 */
public class HorseChess {

    private static int X = 8;//棋盘的列数
    private static int Y = 8;//棋盘的行数

    //棋盘数组，标记棋盘的各个位置是否被访问
    private static boolean[] visited;

    //是否已经完成，所有位置已经被访问
    private static boolean finished;


    /**
     * 计算当前点可以走的下一个点
     * 使用贪心算法对结果进行非递减排序
     *
     * @param current
     * @return
     */
    public static List<Point> next(Point current) {
        List<Point> list = new ArrayList<>();
        //创建point
        Point p1 = new Point();
        //当x=2时 ，-2  =0 位于左边第一列 当x=6时 +1 =7 已经是右边最后一列了，必须小于X
        if ((p1.x = current.x - 2) >= 0 && (p1.y = current.y - 1) >= 0) {//5
            list.add(new Point(p1));
        }
        if ((p1.x = current.x - 2) >= 0 && (p1.y = current.y + 1) < Y) {
            list.add(new Point(p1));
        }
        if ((p1.x = current.x - 1) >= 0 && (p1.y = current.y - 2) >= 0) {//6
            list.add(new Point(p1));
        }
        if ((p1.x = current.x - 1) >= 0 && (p1.y = current.y + 2) < Y) {//3
            list.add(new Point(p1));
        }
        //==================================================================
        if ((p1.x = current.x + 1) < X && (p1.y = current.y - 2) >= 0) {//7
            list.add(new Point(p1));
        }
        if ((p1.x = current.x + 1) < X && (p1.y = current.y + 2) < Y) {//2
            list.add(new Point(p1));
        }
        if ((p1.x = current.x + 2) < X && (p1.y = current.y - 1) >= 0) {//0
            list.add(new Point(p1));
        }
        if ((p1.x = current.x + 2) < X && (p1.y = current.y + 1) < Y) {//1
            list.add(new Point(p1));
        }
        return list;
    }

    /**
     * 根据当前这一步的所有下一步的选择数目进行非递减排序
     * 选择当前下一步的下一步的可选性最少的选择，减少回溯
     * 前-后 升序
     * 后-前 降序
     */
    public static void sort(List<Point> list) {
        list.sort((p1, p2) -> {
            //获取到p1的下一步的所有位置
            int size1 = next(p1).size();
            int size2 = next(p2).size();
            if (size1 < size2) {
                return -1;
            } else if (size1 == size2) {
                return 0;
            } else {
                return 1;
            }
        });
    }

    /**
     * @param chess  棋盘
     * @param row    当前位置的行
     * @param column 当前位置的列
     * @param step   当前第几步 初始从1开始
     */
    public static void travel(int[][] chess, int row, int column, int step) {
        chess[row][column] = step;
        //row 4  X=8  column=4   => 36
        //标记已访问
        visited[row * X + column] = true;
        //获取当前位置可以走的下一个位置
        List<Point> next = next(new Point(column, row));
        sort(next);
        //遍历next
        while (!next.isEmpty()) {
            //取出一个可以走的位置
            Point p = next.remove(0);
            //判断该点是否已经被访问
            if (!visited[p.y * X + p.x]) {
                travel(chess, p.y, p.x, step + 1);
            }
        }
        //判断是否完成任务
        //step < X * Y 有2种：1是棋盘到目前位置仍然没有走完，2是棋盘处于回溯过程
        if (step < X * Y && !finished) {
            chess[row][column] = 0;
            visited[row * X + column] = false;
        } else {
            finished = true;
        }
    }

    public static void main(String[] args) {
        //创建棋盘
        int[][] chess = new int[Y][X];
        visited = new boolean[X * Y];
        int row = 1;//初始化
        int column = 1;//初始列
        long start = System.currentTimeMillis();
        travel(chess, row - 1, column - 1, 1);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) / 1000 + "秒");
        System.out.println("棋盘");
        for (int[] rows : chess) {
            for (int columnj : rows) {
                System.out.printf("%d\t", columnj);
            }
            System.out.println();
        }
    }

}
