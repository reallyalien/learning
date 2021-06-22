package com.ot.algorithm.shortestpath;

import java.util.Arrays;

/**
 * 弗洛伊德算法最短路径问题
 * 各个顶点到各顶点的距离
 */
public class Floyd {

    private static final int N = 65535;

    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = {
                {0, 5, 7, N, N, N, 2},
                {5, 0, N, 9, N, N, 3},
                {7, N, 0, N, 8, N, N},
                {N, 9, N, 0, N, 4, N},
                {N, N, 8, N, 0, 5, 4},
                {N, N, N, 4, 5, 0, 6},
                {2, 3, N, N, 4, 6, 0},
        };
        Graph graph = new Graph(vertex, matrix);
        graph.show();
        graph.floyd();
        graph.show();
    }

    static class Graph {
        private char[] vertex;//存放顶点的数组
        private int[][] dis;//保存各个从各个顶点出发到其他顶点的距离
        private int[][] pre;//保存到达各个顶点的前驱节点

        /**
         * @param vertex 顶点数组
         * @param matrix 初始邻接矩阵
         */
        public Graph(char[] vertex, int[][] matrix) {
            this.vertex = vertex;
            this.dis = matrix;
            this.pre = new int[vertex.length][vertex.length];
            //初始化pre
            for (int i = 0; i < pre.length; i++) {
                Arrays.fill(pre[i], i);
            }
        }

        public void show() {
            System.out.println("dis");
            System.out.println();
            for (int i = 0; i < dis.length; i++) {
                for (int j = 0; j < dis[i].length; j++) {
                    System.out.print("(" + vertex[i] + "-" + vertex[j] + "->" + dis[i][j] + ")" + "\t");
                }
                System.out.println();
            }
            System.out.println("pre");
            for (int i = 0; i < pre.length; i++) {
                for (int j = 0; j < pre[i].length; j++) {
                    System.out.printf("%7d\t", pre[i][j]);
                }
                System.out.println();
            }
        }

        public void floyd() {
            int len = 0;
            //从中间顶点遍历,k就是下标
            for (int k = 0; k < dis.length; k++) {
                //从开始节点i开始出发，经过k这个中间节点
                for (int i = 0; i < dis.length; i++) {
                    //到达终点j
                    for (int j = 0; j < dis.length; j++) {
                        len = dis[i][k] + dis[k][j]; //=>从i顶点出发经过k节点，到达j的距离
                        if (len < dis[i][j]) {
                            //如果len小于i到j直连的距离，更新距离
                            dis[i][j] = len;
                            //更新前驱节点
                            pre[i][j] = pre[k][j];

                        }
                    }
                }
            }
        }
    }
}
