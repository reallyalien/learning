package com.ot.line.order;

/**
 * 环形队列:
 * 环形链表的很多操作都会涉及到取余运算（x%array.length）array.length为数组的长度。
 * 其中就包括求链表的长度。x%array.length的取值范围是[0,array.length-1]。所以链表长度的取值范围也应该是[0,array.length-1]。
 */
public class CircleQueue {
    private int maxSize;//数组的最大容量
    private int front;//队列头元素的位置
    private int rear;//队列尾部的元素的下一个,更准确的说rear一直指向动态的预留空间,预留空间其实一直在改变
    //如果不预留空间，队列满还是空的判断条件是一样的，无法做除法运算
    private int[] arr;//实际存储元素的大小只有maxSize-1个元素

    public CircleQueue(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[this.maxSize];
    }

    public CircleQueue() {
    }

    //判断队列是否满
    public boolean isFull() {
        return size() == (maxSize - 1);
    }

    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    //判断队列是否为空
    public boolean isEmpty() {
        return front == rear;
    }

    public void add(int n) {
        if (isFull()) {
            System.out.println("队列已满");
            return;
        }
        arr[rear] = n;
        rear = (rear + 1) % maxSize;
    }

    //出队
    public int get() {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int value = arr[front];
        arr[front] = 0;
        front = (front + 1) % maxSize;
        return value;
    }

    //显示队列所有数据
    public void show() {
        if (isEmpty()) {
            System.out.println("队列为空，没有数据");
        }
        for (int i = front; i < front + size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    public int head() {
        if (isEmpty()) {
            throw new NullPointerException();
        }
        return arr[front];
    }


    public static void main(String[] args) {
        CircleQueue queue = new CircleQueue(4);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.get();
        queue.add(5);
        queue.get();
        queue.get();
        queue.add(6);
        queue.add(7);
        queue.get();
        queue.get();
        queue.get();
        System.out.println(queue);
    }
}
