package com.ot.noline.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * DFS depth first search
 */
public class Graph {


    public static void main(String[] args) {
        int n = 5;
        String[] vertex = {"A", "B", "C", "D", "E"};
        Graph graph = new Graph(n);
        //循环添加顶点
        for (String s : vertex) {
            graph.insertVertex(s);
        }
        //循环添加边
        //A-B A-C B-C B-D B-E
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);
        /*
        [0, 1, 1, 0, 0]
        [1, 0, 1, 1, 1]
        [1, 1, 0, 0, 0]
        [0, 1, 0, 0, 0]
        [0, 1, 0, 0, 0]
         */
        graph.show();
        //测试深度遍历
//        graph.dfs();
        //测试广度
        System.out.println("广度");
        graph.wfs();
    }

    private List<String> vertexList;//存储顶点的集合
    private int[][] edges;//存储图对应的邻接矩阵
    private int numOfEdges;//边的数目
    private boolean[] visited;//记录是否被访问

    public Graph(int n) {
        //初始化
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
        numOfEdges = 0;
        visited = new boolean[n];
    }

    /**
     * 插入节点
     */
    public void insertVertex(String val) {
        vertexList.add(val);
    }

    /**
     * @param v1 点的下标，第几个顶点   A ->B    A对应的是0  B对应的是1
     * @param v2 添加边
     */
    public void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    /**
     * 返回节点的数目
     *
     * @return
     */
    public int getNumOfVertex() {
        return vertexList.size();
    }

    /**
     * 获取边的个数
     *
     * @return
     */
    public int getNumOfEdges() {
        return numOfEdges;
    }

    /**
     * 返回节点对应的数  0->A   1->B
     */
    public String getValueByIndex(int i) {
        return vertexList.get(i);
    }

    /**
     * 返回v1和v2的权值
     */
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    /**
     * 展示数据
     */
    public void show() {
        for (int[] edge : edges) {
            System.out.println(Arrays.toString(edge));
        }
    }

    /**
     * 获取邻接节点的下标
     *
     * @return 存在返回下标，否则返回-1
     */
    public int getFirstNeighbor(int index) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (edges[index][i] == 1) {
                //下一个邻接点存在
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据前一个邻接节点的下标，来获取下一个邻接节点的下标
     */
    public int getNextNeighbor(int v1, int v2) {
        for (int i = v2 + 1; i < vertexList.size(); i++) {
            if (edges[v1][i] == 1) {
                return i;
            }
        }
        return -1;
    }

    //对dfs进行重载
    public void dfs() {
        //遍历所有的节点进行dfs
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!visited[i]) {
                dfs(visited, i);
            }
        }
    }

    /**
     * 深度优先：
     * 1.访问初始节点v，并标记已访问
     * 2.查找v的邻接节点w
     * 3.若w存在，继续执行4，如果w不存在，则回到第1步，将从v的下一个节点继续
     * 4.若w未被访问，对w进行深度优先遍历
     * 5.查找节点v的w邻接节点的下一个邻接节点，转到步骤3
     * <p>
     * index开始是0
     */
    public void dfs(boolean[] visited, int v) {
        //访问该节点
        System.out.print(getValueByIndex(v) + "->");
        //将该节点设置为已访问
        visited[v] = true;
        //以当前节点深度优先遍历
        //查找v节点的邻接节点w
        int w = getFirstNeighbor(v);
        //有邻接节点
        while (w != -1) {
            //没有访问
            if (!visited[w]) {
                dfs(visited, w);
            }
            //就算w被访问过，继续访问，已经被访问过
            w = getNextNeighbor(v, w);
        }
    }

    /**
     * 广度优先
     */
    public void wfs(boolean[] visited, int i) {
        int u;//表示队列的头节点对应的下标
        int w;//邻接节点的下标
        //队列，记录节点访问的顺序
        LinkedList<Integer> queue = new LinkedList<>();
        //访问该节点
        System.out.print(getValueByIndex(i) + "->");
        visited[i] = true;//标记已访问
        //将节点加入队列
        queue.offer(i);
        while (!queue.isEmpty()) {
            u = queue.poll();
            //得到第一个邻接点的下标
            w = getFirstNeighbor(u);
            while (w != -1) {//存在
                if (!visited[w]) {
                    System.out.println(getValueByIndex(w) + "->");
                    visited[w] = true;//标记已访问
                    queue.offer(w);
                }
                //以u为起点，找w后后面的
                w = getNextNeighbor(u, w);
            }
        }
    }

    public void wfs() {
        for (int i = 0; i < vertexList.size(); i++) {
            if (!visited[i]) {
                wfs(visited, i);
            }
        }
    }
}
