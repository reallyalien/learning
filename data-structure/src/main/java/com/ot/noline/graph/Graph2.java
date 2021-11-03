package com.ot.noline.graph;

import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Graph2 {

    private List<Integer> vertexList;
    private int[][] edges;
    private int numOfEdges;
    private boolean[] visited;

    public Graph2(int n) {
        vertexList = new ArrayList<>(n);
        edges = new int[n][n];
        numOfEdges = 0;
        visited = new boolean[n];
    }

    public void addVertex(int val) {
        vertexList.add(val);
    }

    public void addEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    public int getVertexNum() {
        return vertexList.size();
    }

    public int getNumOfEdges() {
        return numOfEdges;
    }

    public int getValByIndex(int i) {
        return vertexList.get(i);
    }

    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    public int getFirst(int v) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (edges[v][i] != 1) {
                return i;
            }
        }
        return -1;
    }

    public int getNext(int v, int w) {
        for (int i = w + 1; i < vertexList.size(); i++) {
            if (edges[v][i] == 1) {
                return i;
            }
        }
        return -1;
    }

    public void dfs() {
        for (int i = 0; i < vertexList.size(); i++) {
            if (!visited[i]) {
                dfs(visited, i);
            }
        }
    }

    public void dfs(boolean[] visited, int i) {
        System.out.print(getValByIndex(i) + "->");
        visited[i] = true;
        int w = getFirst(i);
        while (w != -1) {
            if (!visited[w]) {
                dfs(visited, w);
            }
            w = getNext(i, w);
        }
    }
    public void wfs(){
        for (int i = 0; i < vertexList.size(); i++) {
            if (!visited[i]){
                wfs(visited,i);
            }
        }
    }

    public void wfs(boolean[] visited, int i) {
        LinkedList<Integer> queue = new LinkedList<>();
        System.out.print(getValByIndex(i) + "->");
        visited[i] = true;
        queue.offer(i);
        while (!queue.isEmpty()) {
            Integer v = queue.poll();
            int w = getFirst(v);
            while (w != -1) {
                if (!visited[w]) {
                    System.out.print(getValByIndex(w) + "->");
                    queue.offer(w);
                    visited[w] = true;
                }
                w = getNext(v, w);
            }
        }
    }

    public void show() {
        for (int[] edge : edges) {
            System.out.println(Arrays.toString(edge));
        }
    }

    public static void main(String[] args) {
        int n = 8;
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        Graph2 graph2 = new Graph2(n);
        for (int i = 0; i < arr.length; i++) {
            graph2.addVertex(arr[i]);
        }
        graph2.addEdge(0, 1, 1);
        graph2.addEdge(0, 2, 1);
        graph2.addEdge(1, 3, 1);
        graph2.addEdge(1, 4, 1);
        graph2.addEdge(3, 7, 1);
        graph2.addEdge(4, 7, 1);
        graph2.addEdge(2, 5, 1);
        graph2.addEdge(2, 6, 1);
        graph2.addEdge(5, 6, 1);
        graph2.show();
//        graph2.dfs();
        System.out.println(1);
        graph2.wfs();
        System.out.println(2);

    }
}
