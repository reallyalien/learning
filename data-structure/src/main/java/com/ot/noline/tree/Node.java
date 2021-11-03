package com.ot.noline.tree;


import java.util.LinkedList;
import java.util.Stack;

/**
 * @author
 */
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
     * 前序遍历 根结点，左子树，右子树
     */
    public void preOrder() {
        Stack<Node> stack = new Stack<>();
        stack.push(this);
        while (!stack.isEmpty()) {
            Node pop = stack.pop();
            if (pop == null) {
                continue;
            }
            //先打印当前节点
            System.out.println(pop);
            //先放的后出
            stack.push(pop.right);
            stack.push(pop.left);
        }
    }

    /**
     * 中序遍历 左子树，根节点，右子树
     */
    public void infixOrder() {
        Stack<Node> stack = new Stack<>();
        Node cur = this;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            //退出循环，当前节点没有左节点
            if (!stack.isEmpty()) {
                cur = stack.pop();
                //打印当前节点，然后指向右子树，右子树当然也是递归处理，也就是while循环
                System.out.println(cur);
                cur = cur.right;
            }
        }
    }

    /**
     * 前序遍历和后续遍历都需要先把当前节点入栈，后续遍历
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
            //当前节点需要最后弹出，因此先放入栈，
            result.push(pop);
            //下面的是一个道理，先进后出，右先弹出，然后先入result栈，左后弹出，因此后入result栈，pop的时候左先出来，然后是右，最后是
            //当前节点
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
