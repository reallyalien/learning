package com.ot.line.stack;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 后缀表达式
 */
public class PostfixExpression {

    public static void main(String[] args) {
        //(3+4)*5-6 对应的逆波兰表达式 3 4 + 5 * 6 -
//        String str = "3 4 + 5 * 6 -";
        String str = "1 2 3 + 4 * + 5 - ";
        List<String> list = Arrays.asList(str.split(" "));
        Stack<String> stack = new Stack<>();
        for (String s : list) {
            //匹配多位数
            if (s.matches("\\d+")) {
                stack.push(s);
            } else {
                //匹配到符号，出栈2个数计算然后压栈
                int num1 = Integer.parseInt(stack.pop());
                int num2 = Integer.parseInt(stack.pop());
                int result = cal(num1, num2, s);
                stack.push(String.valueOf(result));
            }
        }
        System.out.printf("计算结果%s\n", stack.peek());
    }

    public static int cal(int num1, int num2, String ope) {
        int result = 0;//计算的结果
        switch (ope) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num2 - num1;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num2 / num1;
                break;
        }
        return result;
    }


}
