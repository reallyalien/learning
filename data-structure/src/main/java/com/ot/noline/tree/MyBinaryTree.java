package com.ot.noline.tree;

public class MyBinaryTree {

    private Node root;

    public void setRoot(Node root) {
        this.root = root;
    }

    public static void main(String[] args) {
        MyBinaryTree tree = new MyBinaryTree();
        //创建节点
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        tree.setRoot(node1);
        node1.left = node2;
        node1.right = node3;
        node3.left = node4;
        node3.right = node5;
        System.out.println(node1.height());
    }

}
