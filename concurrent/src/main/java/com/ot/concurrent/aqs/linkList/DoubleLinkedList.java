package com.ot.concurrent.aqs.linkList;


import com.ot.concurrent.aqs.list.List;

public class DoubleLinkedList implements List {

    private final Node header = new Node();
    private final Node tail = new Node();

    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(Object o) {
        add(size, o);
    }

    //添加元素的方法
    private void addBefore(Object o, Node succ) {
        Node pre = succ.pre;//这里第一次插入的时候返回的是尾节点，尾节点的前驱就是header，但此时header为null
        Node newNode = new Node(o, pre, succ);//创建新节点，指明它的直接前驱和直接后继
        succ.pre = newNode;//指明所要插入节点的直接前驱就是我么创建的新节点，第一次的时候succ是tail，这里指明了tail节点的前驱
        if (pre == null) {//只有第一次才会进来这个方法，所以指明头节点的直接后继是我们刚刚创建的节点
            header.next = newNode;
        } else {//当链表不为空的时候，指明pre的下一个节点是我们刚刚创建的节点。
            pre.next = newNode;
        }
        size++;//链表长度+1
    }

    @Override
    public void add(int i, Object e) {
        addBefore(e, node(i));
    }

    //根据编号找节点,第一次找不到就返回首节点，首节点不存储数据，方便对头尾节点操作。
    public Node node(int index) {
        if (index < (size >> 1)) {
            Node p = header;
            //前半部分
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
            return p;
        } else {
            //后半部分
            Node t = tail;
            for (int i = size - 1; i > index; i--) {
                t = t.pre;
            }
            return t;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node p = header;
        sb.append("[");
        for (int i = 0; i < size; i++) {
            p = p.next;
            if (i != size - 1) {
                sb.append(p.data + ",");
            } else {
                sb.append(p.data + "]");
            }
        }
        return sb.toString();
    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (Node x = header; x != null; x = x.next) {
                if (x.data == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node x = header; x != null; x = x.next) {
                if (o.equals(x.data)) {
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    //删除节点的方法
    private void unlink(Node x) {
        Node pre = x.pre;//当前节点的直接前驱
        Node next = x.next;//当前节点的直接后继

        if (pre == null) { //当链表当中没有元素时，remove(null) 的pre就是header，next就是tail，
//            header.next = next;//header指向tail  不需要做任何操作
        } else {
            pre.next = next;//如果header节点不为空,链表至少有一个元素，remove(0):比如说删除index为0的元素
                            //指明要删除元素之前元素的后继
            x.pre = null;
        }

        if (next == null) {//链表为空时，next才可能为空
//            tail.pre = pre;
        } else {
            next.pre=pre;//指明要删除元素之后元素的直接前驱
            x.next=null;
        }
        x.data=null;
        size--;
    }
    //=========================================================================================================================
    @Override
    public boolean contains(Object e) {
        return false;
    }


    private class Node {
        private Object data;
        private Node pre;
        private Node next;

        public Node(Object data, Node pre, Node next) {
            this.data = data;
            this.pre = pre;
            this.next = next;
        }

        public Node() {
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Node getPre() {
            return pre;
        }

        public void setPre(Node pre) {
            this.pre = pre;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

}
