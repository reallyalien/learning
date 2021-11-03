package com.ot.noline.tree;

/**
 * 顺序存储二叉树
 * 1.针对满二叉树来讲，节点的个数为2^n -1 n为树的层数，从1开始计数
 * <p>
 * <p>
 * <p>
 * 下面是针对数组说的，因此n从0开始编号，当然计算的结果也是数组下标
 * 2.第n个元素的左子节点为2 x n +1
 * 2.第n个元素的右子节点为2 x n +2
 * 3.第n个元素的父节点为（n-1）/2
 * n表示树当中的第几个元素 ，从0开始编号
 * <p>
 * <p>
 * 1
 * 2              3
 * 4        5     6         7
 * <p>
 * 前:1 2 4 5 3 6 7
 * 中 4 2 5 1 6 3 7
 * 后:4 5 2 6 7 3 1
 * {1,2,3,4,5,6,7}
 */

/**
 * 给你一个数组arr[1,2,3,4,5,6,7],要求以前序遍历的方式的遍历，数组按层次遍历写入数组
 */
public class OrderBinaryTree {

    private int[] arr;//存储数据节点的数组

    public OrderBinaryTree(int[] arr) {
        this.arr = arr;
    }

    public void preOrder() {
        preOrder(0);
    }

    /**
     * 顺序存储二叉树的前序遍历
     *
     * @param index 数组的下标，也即树中元素的编号
     */
    public void preOrder(int index) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("数组为空，请检查参数");
        }
        //输出当前这个元素
        System.out.println(arr[index]);
        //向左递归遍历
        if ((index * 2 + 1) < arr.length) {
            preOrder(index * 2 + 1);//这里不要传错，传的是下标
        }

        //向右递归遍历
        if ((index * 2 + 2) < arr.length) {
            preOrder(index * 2 + 2);
        }
    }

    public void infixOrder() {
        infixOrder(0);
    }

    /**
     * 中序遍历
     *
     * @param index
     */
    public void infixOrder(int index) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("数组为空，请检查参数");
        }

        //向左递归遍历
        if ((index * 2 + 1) < arr.length) {
            infixOrder(index * 2 + 1);//这里不要传错，传的是下标
        }
        //输出当前这个元素
        System.out.printf("%d\t", arr[index]);
        //向右递归遍历
        if ((index * 2 + 2) < arr.length) {
            infixOrder(index * 2 + 2);
        }
    }


    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        OrderBinaryTree tree = new OrderBinaryTree(arr);
        tree.preOrder(); //4 2 5 1 6 3 7
    }


}
