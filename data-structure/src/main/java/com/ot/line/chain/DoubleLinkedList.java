package com.ot.line.chain;


/**
 * 双向链表
 */
public class DoubleLinkedList<T> {

    private final Node<T> head = new Node<T>();//头节点
    private final Node<T> tail = new Node<T>();//尾节点

    /**
     * 默认添加到末尾
     */
    public void add(T data) {
        Node<T> temp = this.head;
        while (true) {
            if (temp.next == null || temp.next.data == null) {
                break;
            }
            temp = temp.next;
        }
        //上面循环结束时，temp是最后一个节点
        Node<T> newNode = new Node<>(temp, tail, data);
        //指明新节点的前驱节点和后继节点
        temp.next = newNode;
        tail.pre = newNode;
    }

    public void remove(T data) {
        Node<T> temp = this.head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.data.equals(data)) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //在这些方法当中一定要明确循环退出的条件是什么
        //如果找到了，则temp就是所要删除的元素，如果没有找到，temp就是尾指针tail
        if (flag) {
            Node<T> next = temp.next;
            Node<T> pre = temp.pre;
            pre.next = next;
            next.pre = pre;
            temp.data = null;
        } else {
            System.out.println("没有找到要删除的元素");
        }
    }

    public void show() {
        if (head.next == null) {
            System.out.println("链表为空");
        }
        Node<T> temp = head.next;
        while (true) {
            if (temp != null && temp.data != null) {
                System.out.println(temp);
                temp = temp.next;
            } else {
                break;
            }
        }
    }

    static class Node<T> {
        private Node<T> pre;
        private Node<T> next;
        private T data;

        public Node() {
        }

        public Node(Node<T> pre, Node<T> next, T data) {
            this.pre = pre;
            this.next = next;
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }

    public static void main(String[] args) {
        DoubleLinkedList<String> list = new DoubleLinkedList<>();
        list.add("java");
        list.add("py");
        list.add("js");
        list.show();
        //删除元素
        System.out.println("==================");
        list.remove("js");
        list.show();
    }
}
