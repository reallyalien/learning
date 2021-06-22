package com.ot.recursion;

/**
 * 迷宫问题
 */
public class Maze {
    public static void main(String[] args) {
        int[][] maze = new int[8][7];
        //使用1表示墙，2表示路径
        //先把上下置为1
        for (int i = 0; i < 7; i++) {
            maze[0][i] = 1;
            maze[7][i] = 1;
        }
        //左右置为1
        for (int i = 0; i < 8; i++) {
            maze[i][0] = 1;
            maze[i][6] = 1;
        }
        //特殊的位置置为1
        maze[3][1] = 1;
        maze[3][2] = 1;
//        maze[1][2] = 1;
//        maze[2][2] = 1;
        //遍历数组
        print(maze);
        //使用递归找路
        boolean way = findWay(maze, 1, 1);
        System.out.println(way);
        print(maze);

    }

    public static void print(int[][] maze) {
        for (int[] ints : maze) {
            for (int anInt : ints) {
                System.out.print(anInt + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 使用递归回溯 找到通路之后一直会往下调用，在返回true之前，一直向下调用，一旦返回true，所有的调用都会返回，
     * 但是每一层都是一样的，变量都是当前栈中的变量
     * <p>
     * 结束位置：maze[6][5]
     * maze[i][j]=0表示还没有走过
     * maze[i][j]=1表示墙
     * maze[i][j]=2表示走过的路，可以走通
     * maze[i][j]=3表示走过的路，但是走不通
     * 在走迷宫之前，需要确定一个策略 下->右->上->左 不同的策略路径不同
     * 如果该点走不通，进行回溯
     *
     * @param maze 地图
     * @param i    位置i 行
     * @param j    位置j 列
     * @return 找到返回true，否则返回false
     */
    public static boolean findWay(int[][] maze, int i, int j) {
        if (maze[6][5] == 2) {
            //通路已经找到
            return true;
        } else {
            if (maze[i][j] == 0) {
                //还没有走过，按照策略走
                maze[i][j] = 2;//假定该点可以走通
                if (findWay(maze, i + 1, j)) {
                    //向下走一步
                    return true;
                } else if (findWay(maze, i, j + 1)) {
                    //向右走一步
                    return true;
                } else if (findWay(maze, i - 1, j)) {
                    //向上走一步
                    return true;
                } else if (findWay(maze, i, j - 1)) {
                    //向左走一步
                    return true;
                } else {
                    //该点走不通，是死路
                    maze[i][j] = 3;
                    return false;
                }
            } else {
                //maze[i][j]！=0，可能是 1 2 3
                return false;
            }
        }
    }
}
