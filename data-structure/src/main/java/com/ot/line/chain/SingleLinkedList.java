package com.ot.line.chain;

import java.util.Stack;

/**
 * 单向链表
 */
public class SingleLinkedList {


    private Node head = new Node(null, 0, null);

    //不考虑编号，添加到链表的末尾
    private void add(Node node) {
        //head节点不能动
        Node temp = head;
        //遍历链表
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        //退出while循环时，temp指向链表的最后
        temp.next = node;
    }

    private void addByOrder(Node node) {
        //head节点不能动
        Node temp = head;
        //编号是否存在，默认不存在
        boolean flag = false;
        while (true) {
            //第一次直接退出循环，链表最后
            if (temp.next == null) {
                break;
            }
            if (temp.next.no > node.no) {
                //新添加的节点在temp和temp.next之间
                break;
            } else if (temp.next.no == node.no) {
                //新添加的节点与下一个节点编号相同，
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            System.out.println("no为:" + node.no + "节点已经存在");
        } else {
            //编号不存在
            //指明新节点的后继
            node.next = temp.next;
            //指明新节点的前驱
            temp.next = node;
        }
    }

    public void show() {
        if (head.next == null) {
            System.out.println("链表为空");
        }
        Node temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }

    /**
     * 根据编号修改，编号不变，修改name属性
     */
    public void update(Node newNode) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        Node temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.no == newNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.name = newNode.name;
        } else {
            System.out.println("没有找到编号为" + newNode.no + "节点");
        }
    }

    /**
     * 找到删除节点的前一个
     *
     * @param no
     */
    public void remove(int no) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        boolean flag = false;
        Node temp = head;
        while (true) {
            if (temp.next == null) {
                //链表的最后
                break;
            }
            if (temp.next.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //temp是要删除节点的前一个节点，
        if (flag) {
            temp.next = temp.next.next;
        } else {
            System.out.println("要删除的" + no + "节点不存在");
        }
    }

    /**
     * 获取单链表节点个数
     *
     * @return
     */
    public int size() {
        int size = 0;
        Node current = head;
        while (true) {
            if (current.next == null) {
                break;
            }
            size++;
            current = current.next;
        }
        return size;
    }

    /**
     * 获取倒数第k个节点
     * 1.获取size
     * 2.遍历（size-k）个元素就ok    比如有5个元素，求倒数第二个元素，遍历temp到第二个元素
     * header  O  O  O  O  O
     */
    public Node getK(int k) {
        //k的合法性需要校验，暂时不写
        int size = size();
        if (size == 0) return null;
        Node temp = head;
        for (int i = 0; i <= size - k; i++) {
            temp = temp.next;
        }
        return temp;
    }

    /**
     * 翻转单链表->头插法
     */

    public static SingleLinkedList reverse(SingleLinkedList list) {
        SingleLinkedList newList = new SingleLinkedList();
        Node newHead = new Node();
        newList.head = newHead;
        Node oldTemp = list.head;
        while (true) {
            Node newTemp = new Node();
            if (oldTemp.next == null) {
                break;
            }
            //原链表的元素的引用不能变，这里需要将从原链表摘下来的元素赋值给一个新的节点，新的节点插入到新链表当中
            oldTemp = oldTemp.next;
            newTemp.no = oldTemp.no;
            newTemp.name = oldTemp.name;
            if (newHead.next == null) {
                newHead.next = newTemp;
                newTemp.next = null;
            } else {
                newTemp.next = newHead.next;
                newHead.next = newTemp;
            }
        }
        return newList;
    }

    /**
     * 翻转单链表->迭代翻转链表,
     * 没有创建新的链表
     */

    public static SingleLinkedList reverse1(SingleLinkedList list) {
        //初始化3个指针
        Node begin = null;//正在遍历元素的前一个元素
        Node mid = list.head.next; //正在遍历的元素
        Node end = mid.next; //正在遍历元素的下一个元素
        while (true) {
            mid.next = begin;
            //如果mid是最后一个元素，遍历结束
            if (end == null) {
                break;
            }
            //指针先后移动
            begin = mid;
            mid = end;
            end = end.next;
        }
        //遍历结束，mid是最后一个元素，也就是新链表的第一个元素
        list.head.next = mid;
        return list;
    }

    public static void reverseOrder(SingleLinkedList list) {
        Node temp = list.head.next;
        Stack<Node> nodeStack = new Stack<>();
        while (temp != null) {
            nodeStack.push(temp);
            temp = temp.next;
        }
        while (nodeStack.size() > 0) {
            System.out.println(nodeStack.pop());
        }
    }

    static class Node {
        private int no;
        private String name;
        private Node next;

        public Node(Node next, int no, String name) {
            this.next = next;
            this.no = no;
            this.name = name;

        }

        public Node(int no, String name) {
            this.no = no;
            this.name = name;
        }

        public Node() {
        }

        @Override
        public String toString() {
            return "Node{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        Node node1 = new Node(1, "宋江");
        Node node2 = new Node(2, "卢俊义");
        Node node3 = new Node(3, "吴用");
        Node node4 = new Node(4, "公孙胜");
        SingleLinkedList linkedList = new SingleLinkedList();
        linkedList.add(node1);
        linkedList.add(node2);
        linkedList.add(node3);
        linkedList.add(node4);

//        linkedList.addByOrder(node4);
//        linkedList.addByOrder(node2);
//        linkedList.addByOrder(node1);
//        linkedList.addByOrder(node3);

        //
        linkedList.show();

        //获取倒数第k个元素
        Node k = linkedList.getK(1);

        System.out.println("获取倒数第k个元素：" + k);
        System.out.println(linkedList.size());

        //翻转
//        SingleLinkedList reverse = reverse1(linkedList);
//        reverse.show();
        //逆序打印
        SingleLinkedList reverse1 = reverse1(linkedList);
        System.out.println(reverse1);
    }
}
