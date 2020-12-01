package com.ot.line.stack;

/**
 * 数组模拟栈
 */
public class ArrayStack {
    private int maxSize;
    private int top = -1;//栈顶
    private int[] stack;

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    /**
     * 判断栈是否满
     *
     * @return
     */
    public boolean isFull() {
        return top == this.maxSize - 1;
    }

    /**
     * 判断栈是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * 入栈
     *
     * @param n
     */
    public void push(int n) {
        if (isFull()) {
            return;
        }
        stack[++top] = n;
    }

    /**
     * 出栈
     *
     * @return
     */
    public int pop() {
        if (isEmpty()) {
            throw new IllegalArgumentException("栈空");
        }
        //栈中的元素还存在，只不过栈顶top指针已经不指向了，
        return stack[top--];
    }

    public int peek() {
        if (isEmpty()) {
            throw new IllegalArgumentException("栈空");
        }
        return stack[top];
    }

    /**
     * 遍历
     */
    public void show() {
        if (isEmpty()) {
            System.out.println("栈空");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(10);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.pop();
        stack.pop();
        stack.show();
    }
}
