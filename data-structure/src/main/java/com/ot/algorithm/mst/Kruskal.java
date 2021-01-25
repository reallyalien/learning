package com.ot.algorithm.mst;

import javax.swing.text.EditorKit;
import java.util.Arrays;

/**
 * 克鲁斯卡尔算法 公交站问题
 */
public class Kruskal {

    private int edgeNum;//边的个数 12

    private char[] vertex;

    private int[][] matrix;//邻接矩阵

    private static final int INF = 10000;//表示2个顶点不连通


    public Kruskal(char[] vertex, int[][] matrix) {

        this.vertex = new char[vertex.length];
        for (int i = 0; i < vertex.length; i++) {
            this.vertex[i] = vertex[i];
        }

        //初始化二维数组
        this.matrix = new int[vertex.length][vertex.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
        //统计多少条边
        for (int i = 0; i < vertex.length; i++) {
            //注意A-B 和B-A其实是一条边
            for (int j = i + 1; j < vertex.length; j++) {
                //有效的边
                if (this.matrix[i][j] != INF) {
                    this.edgeNum++;
                }
            }
        }
    }

    public void show() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%8d\t", matrix[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * 对边进行排序
     */
    public void sort(EData[] eData) {
        for (int i = 0; i < eData.length - 1; i++) {
            for (int j = 0; j < eData.length - 1 - i; j++) {
                if (eData[j].weight > eData[j + 1].weight) {
                    EData temp = eData[j];
                    eData[j] = eData[j + 1];
                    eData[j + 1] = temp;
                }
            }
        }
    }

    /**
     * @param c 传入顶点的值
     * @return 返回顶点对应的下标，如果找不到返回-1
     */
    public int getPosition(char c) {
        for (int i = 0; i < vertex.length; i++) {
            if (vertex[i] == c) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取图中的边，放入Edata当中，然后才能遍历
     * 通过matrix来获取
     * 形式：[['A','B',12],['B','F',7]]
     *
     * @return
     */
    public EData[] getEdges() {
        int index = 0;
        EData[] eData = new EData[edgeNum];//存放边
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix[i].length; j++) {
                if (matrix[i][j] != INF) {
                    eData[index++] = new EData(vertex[i], vertex[j], matrix[i][j]);
                }
            }
        }
        return eData;
    }

    /**
     * 获取下标为i的顶点的终点，用于后面判断2个顶点的终点是否相同，是否构成回路
     *
     * @param ends 记录了各个顶点对应的终点是哪个，是在我们在遍历过程当中逐渐形成的
     * @param i
     * @return 返回下标为i的这个顶点对应的下标
     */
    public int getEnd(int[] ends, int i) {
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }

    public void kruskal() {
        int index = 0;//表示结果数组的索引
        int[] ends = new int[edgeNum];//用于保存已有最小生成树当中的每个顶点在最小生成树当中的终点x
        //创建结果数组，保存最后的最小生成树
        EData[] result = new EData[vertex.length - 1];
        //获取原始图中所有的有效边的集合
        EData[] edges = getEdges();
        //按照边的权值大小排序
        sort(edges);
        //遍历edges.将边添加到最小生成树，并判断是否构成回路，如果没有，就加入到result当中，
        for (int i = 0; i < edges.length; i++) {
            //获取到第i条边的两个顶点
            int p1 = getPosition(edges[i].start);
            int p2 = getPosition(edges[i].end);
            //获取p1 p2在已有的最小生成树当中的终点
            int m = getEnd(ends, p1);
            int n = getEnd(ends, p2);
            //判断是否构成回路
            if (m != n) {
                //不构成回路，加入
                ends[m] = n;//设置m在已有生成树当中的终点
                result[index++] = edges[i];//有一条边加入到result数组
            }
        }
        for (EData eData : result) {
            System.out.println(eData);
        }
    }

    //========================================================================
    //它的对象实例表示一条边
    static class EData {
        private char start;//边的起点
        private char end;//边的另外一个点
        private int weight;//边的权值

        public EData(char start, char end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "EData{" +
                    "start=" + start +
                    ", end=" + end +
                    ", weight=" + weight +
                    '}';
        }


    }


    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int matrix[][] = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {0, 12, INF, INF, INF, 16, 14},
                /*B*/ {12, 0, 10, INF, INF, 7, INF},
                /*C*/ {INF, 10, 0, 3, 5, 6, INF},
                /*D*/ {INF, INF, 3, 0, 4, INF, INF},
                /*E*/ {INF, INF, 5, 4, 0, 2, 8},
                /*F*/ {16, 7, 6, INF, 2, 0, 9},
                /*G*/ {14, INF, INF, INF, 8, 9, 0}};
        //创建Kruskal实力
        Kruskal kruskal = new Kruskal(vertex, matrix);
        kruskal.show();
        kruskal.kruskal();
    }

}
