package com.ot.noline.tree;

import java.util.*;

public class BinaryTree {

    private Node root;

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

    public void infixOrderByStack() {
        if (root == null) return;
        root.infixOrderByStack();
    }

    public void preOrderByStack() {
        if (root == null) return;
        root.preOrderByStack();
    }

    public void postOrderByStack() {
        if (root == null) return;
        root.postOrderByStack();
    }

    public void levelOrder() {
        if (root == null) return;
        root.levelOrder();
    }

    //===============================================================================================================
    //节点类
    static class Node {
        private int id;
        private String name;
        private Node left;
        private Node right;

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

        /**
         * 前序遍历非递归操作
         */
        public void preOrderByStack() {
            Stack<Node> stack = new Stack<>();
            stack.push(this);
            while (!stack.isEmpty()) {
                Node current = stack.pop();
                if (current == null) continue;
                System.out.println(current);
                stack.push(current.right);
                stack.push(current.left);
            }
        }

        /**
         * 中序遍历非递归操作
         */
        public void infixOrderByStack() {
            Stack<Node> stack = new Stack<>();
            Node current = this;
            while (current != null || !stack.isEmpty()) {
                while (current != null) {//处理所有左节点
                    stack.push(current);
                    current = current.left;

                }//退出循环的时候，current=null
                if (!stack.isEmpty()) {//执行到这里，栈顶元素没有左孩子或者左子树都被访问了
                    current = stack.pop();
                    System.out.println(current);
                    current = current.right;//处理右节点，还得遍历它的左孩子，
                }
            }
        }

        public void postOrderByStack() {
            List<Node> list = new ArrayList<>();
            Stack<Node> stack = new Stack<>();
            stack.push(this);
            while (!stack.isEmpty()) {
                Node current = stack.pop();
                if (current == null) continue;
                list.add(current);
                stack.push(current.left);
                stack.push(current.right);
            }
            Collections.reverse(list);
            for (Node node : list) {
                System.out.println(node);
            }
        }

        /**
         * 按层次遍历
         */
        public void levelOrder() {
            LinkedList<Node> queue = new LinkedList<>();
            queue.offer(this);
            while (queue.size() != 0) {
                int len = queue.size();
                for (int i = 0; i < len; i++) {
                    Node temp = queue.poll();
                    System.out.println(temp);
                    if (temp.left != null) {
                        queue.offer(temp.left);
                    }
                    if (temp.right != null) {
                        queue.offer(temp.right);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        //创建节点
        Node root = new Node(1, "宋江");
        Node node1 = new Node(2, "吴用");
        Node node2 = new Node(3, "卢俊义");
        Node node3 = new Node(4, "林冲");
        Node node4 = new Node(5, "关胜");
        binaryTree.setRoot(root);
        //手动先创建二叉树
        root.left = node1;
        root.right = node2;
        node2.right = node3;
        node2.left = node4;
        //测试遍历
//        System.out.println("前序遍历");
//        binaryTree.preOrder();
//        System.out.println("==================");
//        System.out.println("中序遍历");
//        binaryTree.infixOrder();
//        System.out.println("==================");
//        System.out.println("后序遍历");
//        binaryTree.postOrder();
        //测试查找
//        System.out.println(binaryTree.preFind(3));
//        System.out.println(binaryTree.infixFind(2));
//        System.out.println(binaryTree.postFind(40));
        //测试删除
//        binaryTree.deleteNode2(3);
//        binaryTree.preOrder();
        //测试中序非递归
//        binaryTree.infixOrderByStack();
        //测试前序非递归
//        binaryTree.preOrderByStack();
//        binaryTree.postOrderByStack();
        //按层次遍历
        binaryTree.levelOrder();
    }

}
