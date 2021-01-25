package com.ot.algorithm.shortestpath;

import java.util.Arrays;

/**
 * 迪杰斯特拉算法 求最短路径问题
 */
public class Dijkstra {

    private static final int N = 65535;

    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = {
                {N, 5, 7, N, N, N, 2},
                {5, N, N, 9, N, N, 3},
                {7, N, N, N, 8, N, N},
                {N, 9, N, N, N, 4, N},
                {N, N, 8, N, N, 5, 4},
                {N, N, N, 4, 5, N, 6},
                {2, 3, N, N, 4, 6, N},   
        };
        Graph graph = new Graph(vertex, matrix);
        graph.show();
        graph.dijkstra(2);
    }

    //=============================================
    static class Graph {
        private char[] vertex;//顶点数组
        private int[][] matrix;//邻接矩阵
        VisitedVertex vv = null;

        public Graph(char[] vertex, int[][] matrix) {
            this.vertex = vertex;
            this.matrix = matrix;
        }

        public void show() {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    System.out.printf("%10d\t", matrix[i][j]);
                }
                System.out.println();
            }
        }

        /**
         * 算法实现
         *
         * @param index 出发顶点对应的下标
         */
        public void dijkstra(int index) {
            vv = new VisitedVertex(vertex.length, index);
            //更新index下标顶点到周围节点的距离和前驱节点
            update(index);
            for (int i = 1; i < vertex.length; i++) {
                //选择并返回新的访问节点
                index = vv.updateArr();
                update(index);
            }
            vv.show();
            System.out.println("最短路径："+vv.getPath());
        }

        /**
         * 更新index顶点到周围节点的距离和周围顶点的前驱节点
         */
        public void update(int index) {
            int len = 0;
            vv.alreadyArr[index] = 1;
            //访问当前这一层能够访问到的节点

            for (int i = 0; i < matrix[index].length; i++) {
                //从index顶点到i的距离+出发顶点到index这个顶点的距离
                len = matrix[index][i] + vv.getDis(index);
                //如果i顶点没有被访问过，并且len小于从出发顶点到i的距离
                if (!vv.in(i) && len < vv.getDis(i)) {
                    //更新i顶点的前驱节点是index
                    vv.updatePre(i, index);
                    //更新出发顶点到i的距离
                    vv.updateDis(i, len);
                }
            }
        }
    }

    //==============================================
    static class VisitedVertex {
        //记录各个顶点是否被访问，1表示访问，0表示未访问
        private int[] alreadyArr;

        //每个下标对应的值是前一个顶点的下标，会动态更新
        private int[] preVisited;

        //记录从出发顶点到各个顶点的距离
        private int[] dis;

        private VisitedVertex(int length, int index) {
            this.alreadyArr = new int[length];
            this.preVisited = new int[length];
            this.dis = new int[length];
            //初始化
            Arrays.fill(this.dis, N);
            //当前出发顶点到自己的距离为0
            this.dis[index] = 0;
        }

        /**
         * 判断index顶点是否被访问
         *
         * @return 访问过返回true
         */
        public boolean in(int index) {
            return alreadyArr[index] == 1;
        }

        /**
         * 更新触发顶点到index节点的距离
         *
         * @param index
         * @param len
         */
        public void updateDis(int index, int len) {
            this.dis[index] = len;
        }

        /**
         * 更新顶点的前驱节点
         *
         * @param pre
         * @param index
         */
        public void updatePre(int index, int pre) {
            this.preVisited[index] = pre;
        }

        /**
         * 获取出发顶点到index顶点的距离
         *
         * @param index
         */
        public int getDis(int index) {
            return this.dis[index];
        }

        //继续选择新的访问点，比如G点完了之后，选取A点进行按层次遍历，注意不是出发顶点，此时出发顶点依旧是G
        public int updateArr() {
            int min = N;
            int index = 0;
            for (int i = 0; i < alreadyArr.length; i++) {
                //没有被访问并且距离最短
                if (alreadyArr[i] == 0 && dis[i] < min) {
                    min = dis[i];
                    index = i;
                }
            }
            //同时更新index被访问
            alreadyArr[index] = 1;
            return index;
        }

        public int getPath(){
            int num=0;
            for (int i = 0; i < dis.length; i++) {
                num+=dis[i];
            }
            return num;
        }
        public void show() {
            System.out.println("alreadyArr:" + Arrays.toString(alreadyArr));
            System.out.println("preVisited:" + Arrays.toString(preVisited));
            System.out.println("dis\t\t  :" + Arrays.toString(dis));
        }
    }
}
