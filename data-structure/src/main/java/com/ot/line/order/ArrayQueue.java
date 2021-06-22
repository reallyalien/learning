package com.ot.line.order;


public class ArrayQueue {
    private int maxSize;//数组的最大容量
    private int front;//队列头元素的前一个位置
    private int rear;//队列尾部的元素
    private int[] arr;

    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[this.maxSize];
        this.front = -1;//指向队列头部的前一个
        this.rear = -1;//直接指向队列尾部
    }

    public ArrayQueue() {
    }

    //判断队列是否满
    public boolean isFull() {
        return rear == maxSize - 1;
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
        rear++;
        arr[rear] = n;
    }

    //出队
    public int get() {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        front++;
        return arr[front];
    }

    //显示队列所有数据
    public void show() {
        if (isEmpty()) {
            System.out.println("队列为空，没有数据");
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("没有数据");
        }
        return arr[front + 1];
    }


    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.show();
        System.out.println(queue.get());
    }
}
