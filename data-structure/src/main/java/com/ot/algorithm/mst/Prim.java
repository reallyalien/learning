package com.ot.algorithm.mst;

import java.util.Arrays;

/**
 * prim算法  MST：最小生成树 7个节点，必有6条边
 */
public class Prim {


    public static void main(String[] args) {
        //测试图的创建
        char[] data = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int vertex = data.length;
        //邻接矩阵使用二维数组描述
        int[][] weight = {
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000},
        };
        Graph graph = new Graph(vertex);
        MinTree minTree = new MinTree();
        minTree.createGraph(graph, vertex, data, weight);
        minTree.show(graph);
        //测试prim算法
        minTree.prim(graph, 5);
    }

    static class MinTree {

        //创建图的邻接矩阵
        public void createGraph(Graph graph, int vertex, char[] data, int[][] weight) {
            for (int i = 0; i < vertex; i++) {
                graph.data[i] = data[i];
                for (int j = 0; j < vertex; j++) {
                    graph.weight[i][j] = weight[i][j];
                }
            }
        }

        public void show(Graph graph) {
            //显示图的邻接矩阵
            for (int[] ints : graph.weight) {
                System.out.println(Arrays.toString(ints));
            }
        }

        /**
         * 编写prim算法。得到最小生成树
         *
         * @param graph 图
         * @param v     从哪个顶点开始   'A'->0    'B'->1
         */
        public void prim(Graph graph, int v) {
            //表示已经访问过的节点的数组，是否被访问，默认都未访问都是0
            int[] visited = new int[graph.vertex];
            //标记当前节点被访问
            visited[v] = 1;
            //h1 h2记录2个顶点的下标
            int h1 = -1;
            int h2 = -1;
            int minWeight = 10000;//初始化一个较大值，后面在遍历过程当中会被替换
            //因为有graph.vertex顶点，这个结束之后会有graph.vertex-1条边
            for (int k = 1; k < graph.vertex; k++) {     //遍历所有的节点
                //这个双层循环还是会遍历整个图去寻找满足条件的节点
                //每一次生成的子图和哪个节点的距离最近
                for (int i = 0; i < graph.vertex; i++) { //遍历已经访问过的节点
                    for (int j = 0; j < graph.vertex; j++) { //遍历还没有访问过的节点
                        if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight) {
                            //替换
                            minWeight = graph.weight[i][j];
                            h1 = i;
                            h2 = j;
                        }
                    }
                }
                //找到一条边是最小的
                System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + ">权值是：" + minWeight);
                //将找到的节点标记为已访问 h1的节点已经访问过
                visited[h2] = 1;
                //重置minWeight
                minWeight = 10000;
            }
        }
    }

    static class Graph {
        int vertex;//表示图节点的个数
        char[] data;//存放节点数据
        int[][] weight;//邻接矩阵。保存我们的边

        public Graph(int size) {
            vertex = size;
            data = new char[vertex];
            weight = new int[vertex][vertex];
        }
    }
}
