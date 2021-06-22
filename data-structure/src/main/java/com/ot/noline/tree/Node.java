package com.ot.noline.tree;


import java.util.LinkedList;
import java.util.Stack;

public class Node {

    public int id;
    public Node left;
    public Node right;

    public Node(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                '}';
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        Stack<Node> stack = new Stack<>();
        stack.push(this);
        while (!stack.isEmpty()) {
            Node pop = stack.pop();
            if (pop == null) {
                continue;
            }
            System.out.println(pop);
            stack.push(pop.right);
            stack.push(pop.left);
        }
    }

    /**
     * 中序遍历
     */
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

    /**
     * 后续遍历
     */
    public void postOrder() {
        Stack<Node> result = new Stack<>();
        Stack<Node> temp = new Stack<>();
        temp.push(this);
        while (!temp.isEmpty()) {
            Node pop = temp.pop();
            if (pop == null) {
                continue;
            }
            result.push(pop);
            temp.push(pop.left);
            temp.push(pop.right);
        }
        while (!result.isEmpty()) {
            System.out.println(result.pop());
        }
    }

    /**
     * 按层次遍历
     */
    public void levelOrder() {
        LinkedList<Node> queue = new LinkedList<>();
        queue.offer(this);
        while (!queue.isEmpty()) {
            for (int i = 0; i < queue.size(); i++) {
                Node poll = queue.poll();
                System.out.println(poll);
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
        }
    }

    /**
     * 获取树的高度
     *
     * @return
     */
    public int height() {
        //如果当前节点根节点没有左右子树，即高度就为1，因此得+1
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }
}
