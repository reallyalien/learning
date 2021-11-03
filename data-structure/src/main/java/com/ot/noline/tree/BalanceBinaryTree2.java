package com.ot.noline.tree;

import java.util.Stack;

public class BalanceBinaryTree2 {

    private Node root;

    public static void main(String[] args) {
        BalanceBinaryTree2 tree = new BalanceBinaryTree2();
        int[] arr = new int[1000];//双旋转
        for (int i = 0; i < 1000; i++) {
            arr[i] = i;
        }

        for (int i = 0; i < arr.length; i++) {
            tree.add(new BalanceBinaryTree2.Node(arr[i]));
        }
//        tree.infix();
        System.out.println("树的高度：" + tree.root.height());
        System.out.println("左子树的高度：" + tree.root.leftHeight());
        System.out.println("右子树的高度：" + tree.root.rightHeight());
        System.out.println("根节点是：" + tree.root);

    }

    public void add(Node node) {
        if (node == null) return;
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    public void infix() {
        root.infixOrder();
    }

    static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }

        /**
         * 添加节点的方法
         *
         * @param node
         */
        public void add(Node node) {
            if (node.value <= this.value) {
                if (this.left == null) {
                    this.left = node;
                } else {
                    this.left.add(node);
                }
            } else {
                if (this.right == null) {
                    this.right = node;
                } else {
                    this.right.add(node);
                }
            }
            //判断当前树的高度
            if (rightHeight() - leftHeight() > 1) {
                //左旋
                if (right != null && right.leftHeight() > right.rightHeight()) {
                    this.right.rightRa();
                }
                leftRa();
            } else if (leftHeight() - rightHeight() > 1) {
                //右旋
                if (left != null && left.rightHeight() > left.leftHeight()) {
                    left.leftRa();
                }
                rightRa();
            }
        }

        //左子树高度
        public int leftHeight() {
            if (this.left == null) return 0;
            return this.left.height();
        }

        //右子树高度
        public int rightHeight() {
            if (this.right == null) return 0;
            return this.right.height();
        }

        public int height() {
            return Math.max(
                    left == null ? 0 : this.left.height(),
                    right == null ? 0 : this.right.height()
            ) + 1;
        }

        /**
         * 左旋
         */
        public void leftRa() {
            //以根节点
            Node node = new Node(value);
            node.left = left;
            node.right = right.left;
            value = right.value;
            right = right.right;
            left = node;
        }

        //右旋
        public void rightRa() {
            Node node = new Node(value);
            node.right = right;
            node.left = left.right;
            value = left.value;
            right = node;
            left = left.left;
        }

        public void infixOrder() {
            Stack<Node> stack = new Stack<>();
            Node cur = this;
            while (cur != null || !stack.isEmpty()) {
                while (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                }
                if (!stack.isEmpty()) {
                    cur = stack.pop();
                    System.out.println(cur);
                    cur = cur.right;
                }
            }
        }
    }
}
