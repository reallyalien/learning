package com.ot.noline.tree;

/**
 * 线索化二叉树
 */

/**
 *                   1
 *              3          6
 *         8      10   14
 *
 *
 */
public class ThreadBinaryTree {

    private Node root;

    //    为了实现线索化，我们需要创建给当前节点的前驱引用
    private Node pre;

    public void setRoot(Node root) {
        this.root = root;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        }
    }

    /**
     * 前序查找
     */
    public Node preFind(int id) {
        if (this.root != null) {
            return this.root.preFind(id);
        }
        return null;
    }

    /**
     * 前序查找
     */
    public Node infixFind(int id) {
        if (this.root != null) {
            return this.root.infixFind(id);
        }
        return null;
    }

    /**
     * 前序查找
     */
    public Node postFind(int id) {
        if (this.root != null) {
            return this.root.postFind(id);
        }
        return null;
    }

    public void deleteNode(int id) {
        if (this.root != null) {
            if (root.id == id) {
                //如果删除的是根节点
                this.root = null;
            } else {
                //如果不是删除的根节点，递归查询删除
                this.root.deleteNode(id);
            }
        }
        System.out.println("当前树为空");
    }

    public void deleteNode2(int id) {
        if (this.root != null) {
            if (root.id == id) {
                //如果删除的是根节点
                this.root = null;
            } else {
                //如果不是删除的根节点，递归查询删除
                this.root.deleteNode2(id);
            }
        }
        System.out.println("当前树为空");
    }

    public void threadNode() {
        threadNode(root);
    }

    /**
     * 中序线索化
     *
     * @param node 当前需要线索化的节点
     */
    public void threadNode(Node node) {
        if (node == null) {
            return;
        }
        //1.先线索化左子树
        threadNode(node.left);
        //2.当前节点
        //处理当前节点的前驱节点
        if (node.left == null) {
            //让当前节点的左指针指向前驱节点
            node.left = pre;
            node.leftType = 1;//前驱
        }
        //处理后继节点,本次不能处理，得递归到下次处理
        if (pre != null && pre.right == null) {
            pre.right = node;
            pre.rightType = 1;
        }
        //每处理一个节点，让当前节点成为下一个节点的前驱节点
        pre = node;
        //3.线索化右子树
        threadNode(node.right);
    }

    /**
     * 遍历线索化二叉树
     */
    public void threadTreeList() {
        //定义一个遍历，存储当前遍历的节点，从root开始
        Node node = root;
        while (node != null) {
            //循环找到leftType=1的节点，第一个找到的是8,该节点是按照线索化处理后的有效节点
            while (node.leftType == 0) {
                node = node.left;
            }
            //打印当前节点
            System.out.println(node);
            //如果当前的右指针指向的是后继节点，就一直输出
            while (node.rightType == 1) {
                node = node.right;
                System.out.println(node);
            }
            //现在right指向的是右节点
            node = node.right;
        }
    }

    //===============================================================================================================
    static class Node {
        private int id;
        private String name;
        private Node left;
        private Node right;
        //规定 leftType:0 左子树 1前驱节点
        //     rightType: 0右子树，1后继节点
        private int leftType;
        private int rightType;

        public Node(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        /**
         * 前序遍历
         */
        public void preOrder() {
            System.out.println(this);
            //左子树
            if (this.left != null) {
                this.left.preOrder();
            }
            //右子树
            if (this.right != null) {
                this.right.preOrder();
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
         * 后序遍历
         */
        public void postOrder() {
            if (this.left != null) {
                this.left.postOrder();
            }
            if (this.right != null) {
                this.right.postOrder();
            }
            System.out.println(this);
        }

        /**
         * 前序遍历查找
         *
         * @param id
         * @return
         */
        public Node preFind(int id) {
            if (this.id == id) return this;
            Node result = null;
            if (this.left != null) {
                result = this.left.preFind(id);//这里注意不能直接return ，否则会找不到
            }
            if (result != null) {
                return result;
            }
            if (this.right != null) {
                result = this.right.preFind(id);
            }
            if (result != null) {
                return result;
            }
            return result;
        }

        /**
         * 中序遍历查找
         *
         * @param id
         * @return
         */
        public Node infixFind(int id) {

            Node result = null;
            if (this.left != null) {
                result = this.left.infixFind(id);//这里注意不能直接return ，否则会找不到
            }
            if (result != null) {
                return result;
            }
            if (this.id == id) return this;

            if (this.right != null) {
                result = this.right.infixFind(id);
            }
            if (result != null) {
                return result;
            }
            return result;
        }

        /**
         * 中序遍历查找
         *
         * @param id
         * @return
         */
        public Node postFind(int id) {

            Node result = null;
            if (this.left != null) {
                result = this.left.postFind(id);//这里注意不能直接return ，否则会找不到
            }
            if (result != null) {
                return result;
            }


            if (this.right != null) {
                result = this.right.postFind(id);
            }
            if (result != null) {
                return result;
            }
            if (this.id == id) return this;
            return result;
        }

        /**
         * 删除节点，因为树是单向的，因此我们不能判断当前节点是否需要删除，需要判断当前节点的子节点是否需要删除，
         * 这里为了简单起见，如果要删除的节点拥有子节点，则一并删除
         */
        public void deleteNode(int id) {
            //从左子树查询删除
            if (this.left != null && this.left.id == id) {
                this.left = null;
                return;
            }
            //从右子树查询删除
            if (this.right != null && this.right.id == id) {
                this.right = null;
                return;
            }
            //递归删除
            if (this.left != null) {
                this.left.deleteNode(id);
            }
            if (this.right != null) {
                this.right.deleteNode(id);
            }
        }

        /**
         * 当删除非叶子节点的时候，判断当前节点的子树的个数，如果当前节点只有一个子节点，让其子节点代替要被删除的元素
         * 如果当前节点拥有2个子节点则让其左边的节点代替它
         *
         * @param id
         */
        public void deleteNode2(int id) {
            //从左子树查询删除
            if (this.left != null && this.left.id == id) {
                //判断是否是叶子节点
                int childNodeNum = childNodeNum(this.left);
                if (childNodeNum == 0) {
                    //叶子节点
                    this.left = null;
                } else if (childNodeNum == 1) {
                    //只有一个子节点，获取要被删除的子节点，现在无法区分左右，通过是否是null来判断
                    Node left = this.left.left;
                    if (left != null) {
                        this.left = left;
                    } else {
                        this.left = this.left.right;
                    }
                } else {
                    //被删除的节点拥有2个子节点，用左边的节点替代要删除的节点
                    //特别注意需要先将所要删除节点的右节点挂在左节点上方,现在不区分挂在左右,先挂在左边
                    this.left.left.left = this.left.right;
                    this.left = this.left.left;
                }
                return;
            }
            //从右子树查询删除
            if (this.right != null && this.right.id == id) {
                int childNodeNum = childNodeNum(this.right);
                if (childNodeNum == 0) {
                    //叶子节点
                    this.right = null;
                } else if (childNodeNum == 1) {
                    //只有一个子节点，获取要被删除的子节点，现在无法区分左右，通过是否是null来判断
                    Node left = this.right.left;
                    if (left != null) {
                        this.right = left;
                    } else {
                        this.right = this.right.right;
                    }
                } else {
                    //被删除的节点拥有2个子节点，用左边的节点替代要删除的节点
                    this.right.left.left = this.right.right;
                    this.right = this.right.left;
                }
                return;
            }
            //递归删除
            if (this.left != null) {
                this.left.deleteNode(id);
            }
            if (this.right != null) {
                this.right.deleteNode(id);
            }
        }

        /**
         * 获取当前节点的子节点的数目，用来判断是否是叶子节点
         *
         * @param node
         * @return
         */
        public int childNodeNum(Node node) {
            int num = 0;
            Node left = node.left;
            Node right = node.right;
            if (left != null) {
                num++;
            }
            if (right != null) {
                num++;
            }
            return num;
        }
    }

    public static void main(String[] args) {
        //测试线索化二叉树
        Node root = new Node(1, "tom");
        Node node2 = new Node(3, "jack");
        Node node3 = new Node(6, "smith");
        Node node4 = new Node(8, "mary");
        Node node5 = new Node(10, "k");
        Node node6 = new Node(14, "dim");
        //递归创建二叉树
        ThreadBinaryTree tree = new ThreadBinaryTree();
        tree.setRoot(root);
        root.left = node2;
        root.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        //tree.infixOrder();// 8 3 10 1 14 6
        //线索化
        tree.threadNode();
        System.out.println("------");
        //这里已经不能使用原来的方法进行遍历，因为left和right指针已经发生改变，会死循环
        tree.threadTreeList();
    }
}
