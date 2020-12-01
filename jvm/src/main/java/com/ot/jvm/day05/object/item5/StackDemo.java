package com.ot.jvm.day05.object.item5;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class StackDemo {

    public static void main(String[] args) {
        Deque<Object> stack=new LinkedList<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

    }
}
