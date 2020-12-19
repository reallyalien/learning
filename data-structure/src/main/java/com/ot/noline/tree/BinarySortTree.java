package com.ot.noline.tree;

/**
 * 二叉排序树
 * 左子节点的< 节点 < 右子节点
 */
public class BinarySortTree {

    private Node root;

    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree tree = new BinarySortTree();
        for (int i = 0; i < arr.length; i++) {
            tree.add(new Node(arr[i]));
        }
        tree.infixOrder();
        tree.delNode(5);
        tree.delNode(1);
        tree.delNode(9);
        tree.delNode(10);
        tree.delNode(12);
        tree.delNode(7);
        tree.delNode(3);
        tree.delNode(2);
        System.out.println(tree.root);
        System.out.println("===========================");
        tree.infixOrder();
    }

    public void add(Node node) {
        if (node == null) return;
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    public void infixOrder() {
        if (root == null) return;
        root.infixOrder();
    }

    public void preOrder() {
        if (root == null) return;
        root.preOrder();
    }

    public Node search(Integer val) {
        if (root == null) return null;
        return root.search(val);
    }

    public Node findParent(Integer val) {
        if (root == null) return null;
        return root.findParent(val);
    }

    public void delNode(Integer val) {
        if (root == null) return;
        if (root.left == null && root.right == null && root.value == val) {
            //如果只有一个节点
            root = null;
            return;
        }
        //查找到的节点
        Node target = search(val);
        if (target == null) {
            System.out.println("未找到要删除的节点");
            return;
        }
        //查找父节点
        Node parent = findParent(val);
        //如果在删除节点当中parnet为空，说明当前删除的跟节点，直接把根节点指向左节点
        if (parent == null) {
            root = target.left;
            return;
        }
        //如果要删除的是叶子节点
        if (target.left == null && target.right == null) {
            //判断target是父节点的左节点还是右节点
            if (parent.left != null && parent.left.value == val) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else if (target.left != null && target.left != null) {
            //要删除的节点有2个子树
            //从要删除的节点的右子树找到最小的数,一直往左边找
            int rightMin = delRightMin(target.right);
            //替换
            target.value = rightMin;
        } else {
            //删除只有一个子树的节点
            if (target.left != null) {
                //要删除的节点有左子节点
                if (parent.left != null && parent.left.value == val) {
                    parent.left = target.left;
                } else {
                    parent.right = target.left;
                }
            } else {
                //要删除的节点有右子节点
                if (parent.left != null && parent.left.value == val) {
                    parent.left = target.right;
                } else {
                    parent.right = target.right;
                }
            }
        }
    }

    /**
     * 返回指定节点的右子树的最小的值
     * 同时删除最小节点
     */
    public int delRightMin(Node node) {
        Node temp = node;
        //一直向左边找最小的，必然是叶子节点
        while (temp.left != null) {
            temp = temp.left;
        }
        //删除这个节点
        delNode(temp.value);
        return temp.value;
    }

    static class Node {
        private Integer value;
        private Node left;
        private Node right;

        public Node(Integer value) {
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
         */
        public void add(Node node) {
            if (node == null) return;
            if (node.value < this.value) {
                if (this.left == null) {
                    //如果当前节点左子节点为空，直接挂
                    this.left = node;
                } else {
                    //递归向左添加
                    this.left.add(node);
                }
            } else {
                if (this.right == null) {
                    this.right = node;
                } else {
                    this.right.add(node);
                }
            }
        }

        /**
         * 中序遍历
         */
        public void infixOrder() {
            if (this.left != null) {
                this.left.infixOrder();
            }
            System.out.println(this);
            if (this.right != null) {
                this.right.infixOrder();
            }
        }

        /**
         * 前序
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

        /**
         * 查找节点
         *
         * @param value 节点的值
         * @return 如果找到返回该节点，否返回null
         */
        public Node search(Integer value) {
            if (value == this.value) {
                return this;
            } else if (value < this.value) {
                if (this.left != null) {
                    return this.left.search(value);
                }
            } else {
                if (this.right != null) {
                    return this.right.search(value);
                }
            }
            return null;
        }

        /**
         * 查找父节点
         *
         * @param value
         * @return
         */
        public Node findParent(Integer value) {
            if ((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)) {
                return this;
            } else if (this.left != null && value < this.value) {
                return this.left.findParent(value);
            } else if (this.right != null && value >= this.value) {
                return this.right.findParent(value);
            } else {
                return null;
            }
        }

        /**
         * 删除节点，所要删除节点拥有2个子树，从右子树查找最小的节点代替所要删除的节点或者从左子树找到最大的值
         */
        public void delete(Node node) {

        }
    }
}
