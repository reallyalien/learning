package com.ot.noline.tree;


import java.util.LinkedList;
import java.util.Stack;

public class BTree {

    public TreeNode root;

    public void add(TreeNode treeNode) {
        if (root == null) {
            root = treeNode;
        } else {
            root.add(treeNode);
        }
    }

    public static void main(String[] args) {
        BTree tree = new BTree();
        for (int i = 0; i < 20; i++) {
            tree.add(new TreeNode(i + 1));
        }
        System.out.println("树的高度：" + tree.root.height());
        System.out.println("树的左子树高度：" + tree.root.leftHeight());
        System.out.println("树的右子树高度：" + tree.root.rightHeight());
        tree.root.level();
        System.out.println();
    }


    static class TreeNode {
        public TreeNode left;
        public TreeNode right;
        public int val;

        public TreeNode(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    '}';
        }

        public void add(TreeNode treeNode) {
            if (treeNode == null) return;
            if (treeNode.val < this.val) {
                if (this.left == null) {
                    this.left = treeNode;
                } else {
                    this.left.add(treeNode);
                }
            } else {
                if (this.right == null) {
                    this.right = treeNode;
                } else {
                    this.right.add(treeNode);
                }
            }
            //左旋
            if (rightHeight() - leftHeight() > 1) {
                if (left != null && left.leftHeight() > left.rightHeight()) {
                    this.left.rightHeight();
                }
                leftRotate();
                return;
            }
            //右旋
            if (leftHeight() - rightHeight() > 1) {
                if (right != null && right.rightHeight() > right.leftHeight()) {
                    this.right.leftHeight();
                }
                rightRotate();
            }
        }

        public void preOrder() {
            Stack<TreeNode> stack = new Stack<>();
            stack.push(this);
            while (!stack.isEmpty()) {
                TreeNode pop = stack.pop();
                if (pop == null) continue;
                System.out.println(pop);
                stack.push(pop.right);
                stack.push(pop.left);
            }
        }

        public void inOrder() {
            Stack<TreeNode> stack = new Stack<>();
            TreeNode cur = this;
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

        public void postOrder() {
            Stack<TreeNode> result = new Stack<>();
            Stack<TreeNode> stack = new Stack<>();
            stack.push(this);
            while (!stack.isEmpty()) {
                TreeNode pop = stack.pop();
                if (pop == null) continue;
                result.push(pop);
                stack.push(pop.left);
                stack.push(pop.right);
            }
        }

        public void level() {
            LinkedList<TreeNode> queue = new LinkedList();
            queue.offer(this);
            while (!queue.isEmpty()) {
                TreeNode poll = queue.poll();
                System.out.println(poll);
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
        }

        public void leftRotate() {
            TreeNode node = new TreeNode(val);
            node.left = left;
            node.right = right.left;
            val = right.val;
            right = right.right;
            left = node;
        }

        public void rightRotate() {
            TreeNode node = new TreeNode(val);
            node.right = right;
            node.left = left.right;
            val = left.val;
            left = left.left;
            right = node;
        }

        public int leftHeight() {
            if (this.left == null) return 0;
            return this.left.height();
        }

        public int rightHeight() {
            if (this.right == null) return 0;
            return this.right.height();
        }

        public int height() {
            return Math.max(
                    this.left == null ? 0 : this.left.height(),
                    this.right == null ? 0 : this.right.height()
            ) + 1;
        }
    }
}

