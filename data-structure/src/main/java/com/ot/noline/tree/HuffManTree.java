package com.ot.noline.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 赫夫曼树，树的带权路径长度最小的就是赫夫曼树，权值最大的节点离根节点越近
 * 所有叶子节点 ，权值乘以路径长度
 * <p>
 * 如果遇到相同的值，只要满足赫夫曼树，顺序无所谓，WPL（weight path length）是一样的
 */
public class HuffManTree {

    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        List<Node> list = arrToList(arr);
        Node root = createHuffManTree(list);
        root.preOrder();
    }


    /**
     *
     */
    public static List<Node> arrToList(int[] arr){
        List<Node> list = new ArrayList<>();
        Node node = null;
        for (int i = 0; i < arr.length; i++) {
            node = new Node(arr[i]);
            list.add(node);
        }
        return list;
    }
    /**
     * 创建赫夫曼树
     *
     * @param list
     * @param //赫夫曼树的root节点
     */
    public static Node createHuffManTree(List<Node> list) {
        //排序
        while (list.size() > 1) {
            Collections.sort(list);
            //取出根节点权值最小的节点(二叉树)
            Node left = list.get(0);
            Node right = list.get(1);
            //构建一个新的二叉树
            Node parent = new Node(left.value + right.value);
            parent.left = left;
            parent.right = right;
            //从list当中删除处理过的二叉树
            list.remove(left);
            list.remove(right);
            //将parent加入到list
            list.add(parent);
        }
        //返回赫夫曼树的root节点
        return list.get(0);
    }

    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("空树，不能遍历");
        }
    }

    static class Node implements Comparable<Node> {
        public Integer value;//节点权值
        public Character c;//赫夫曼编码当中的字符
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }

        public Node(char c,int value) {
            this.value = value;
            this.c = c;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "c=" + c +
                    ", value=" + value +
                    '}';
        }

        @Override
        public int compareTo(Node o) {
            //从小到大排序
            return this.value - o.value;
        }

        /**
         * 前序遍历
         */
        public void preOrder() {
            System.out.println(this);
            if (this.left != null) {
                this.left.preOrder();
            }
            if (this.right != null) {
                this.right.preOrder();
            }
        }
    }
}
