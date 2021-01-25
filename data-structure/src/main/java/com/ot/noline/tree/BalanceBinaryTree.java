package com.ot.noline.tree;

/**
 * 平衡二叉树
 */
public class BalanceBinaryTree {

    private Node root;

    public static void main(String[] args) {
        BalanceBinaryTree tree = new BalanceBinaryTree();
//        int[] arr = {4, 3, 6, 5, 7, 8};//左旋转
//        int[] arr = {10, 12, 8, 9, 7, 6};//右旋转
        int[] arr = {10, 7, 11, 6, 8, 9};//双旋转
        for (int i = 0; i < arr.length; i++) {
            tree.add(new Node(arr[i]));
        }
        tree.infixOrder();
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
        //如果在删除节点当中parent为空，说明当前删除的跟节点，直接把根节点指向左节点
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
        public Integer value;
        public Node left;
        public Node right;

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
         * 以该节点为根节点的树的高度
         * 左子树或者右子树较高的树高度
         *
         * @return
         */
        public int height() {
            return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
        }

        public int leftHeight() {
            if (left == null) return 0;
            return left.height();
        }

        public int rightHeight() {
            if (right == null) return 0;
            return right.height();
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
            //当添加完一个节点之后，如果右子树的高度-左子树的高度 > 1,左旋转
            if (rightHeight() - leftHeight() > 1) {
                //如果当前的右节点的左子树高度大于右子树
                if (right != null && right.leftHeight() > right.rightHeight()) {
                    //右旋转
                    right.rightRotate();
                }
                leftRotate();
                return;//如果不return走下面的代码可能又不平衡
            }
            //当添加完一个节点之后，如果右子树的高度-左子树的高度 > 1,右旋转
            if (leftHeight() - rightHeight() > 1) {
                //如果当前节点的左子节点的右子树高度大于左子树的高度
                if (left != null && left.rightHeight() > left.leftHeight()) {
                    //左旋转
                    left.leftRotate();
                }
                rightRotate();
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
         * 左旋转
         */
        public void leftRotate() {
            //1、以当前根节点的值创建新的节点
            Node newNode = new Node(value);
            //2、把新节点的左子树设置为当前节点的左子树
            newNode.left = left;
            //3、把新节点的右子树设置为当前节点的右子树的左子树
            newNode.right = right.left;
            //4、把当前节点的值替换成右子节点的值
            value = right.value;
            //5、把当前节点的右子树设置成当前节点右子树的右子树
            right = right.right;
            //6、把当前节点的左子树设置成新的节点
            left = newNode;
        }

        /**
         * 右旋转
         */
        public void rightRotate() {
            //1、以当前根节点的值创建新的节点
            Node newNode = new Node(value);
            //2、把新节点的右子树设置为当前节点的右子树
            newNode.right = right;
            //3、把新节点的左子树子树设置为当前节点的左子树的右子树
            newNode.left = left.right;
            //4、把当前节点的值替换成右子节点的值
            value = left.value;
            //5、把当前节点的左子树设置成当前节点左子树的左子树
            left = left.left;
            //6、把当前节点的右子树设置成新的节点
            right = newNode;
        }

        /**
         * 删除节点，所要删除节点拥有2个子树，从右子树查找最小的节点代替所要删除的节点或者从左子树找到最大的值
         */
        public void delete(BinarySortTree.Node node) {

        }
    }
}
