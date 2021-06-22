package com.ot.line.stack;

/**
 * 中缀表达式
 */
public class Calculator {


    public static void main(String[] args) {
        String express = "2-9-1";
        ArrayStack numStack = new ArrayStack(10);
        ArrayStack opeStack = new ArrayStack(10);
        //定义需要的变量
        int index = 0;//用于扫描
        int num1, num2;
        int ope;
        int result;
        char c = ' ';//每次扫描得到的char
        String keepNum = "";//用于拼接多位数
        while (true) {
            c = express.charAt(index);
            if (isOpe(c)) {
                //判断符号栈是否为空
                if (!opeStack.isEmpty()) {
                    //判断运算符的优先级，如果小于，则从数栈当中弹出2个元素和从符号栈中弹出一个运算符进行运算
                    //因为运算符高的应该先计算
                    if (priority(c) <= priority(opeStack.peek())) {
                        //后弹出来的应该是先入栈的，因此计算减法num2应该在前面
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        ope = opeStack.pop();
                        result = cal(num1, num2, ope);
                        numStack.push(result);
                        //符号入栈
                    } else {
                        //当前操作符的优先级大于，直接入栈
                    }
                }
                opeStack.push(c);
            } else {
                //如果是数，'1'对应的是49
//                numStack.push(c - 48);
                //处理多位数
                keepNum += c;
                while (true) {
                    //如果到了末尾。退出循环
                    if (index == express.length() - 1) {
                        break;
                    }
                    char c1 = express.charAt(++index);
                    //如果是符号则将index--,这里不处理符号，只处理数字
                    if (isOpe(c1)) {
                        index--;
                        break;
                    } else {
                        keepNum += c1;
                    }
                }
                numStack.push(Integer.parseInt(keepNum));
                //得清空
                keepNum = "";
            }
            //让index+1，判断是否到最后
            index++;
            if (index >= express.length()) {
                break;
            }
        }
        //整个表达式都扫描完毕
        while (true) {
            //如果符号栈为空，数栈只有一个值，计算结束
            if (opeStack.isEmpty()) {
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            ope = opeStack.pop();
            result = cal(num1, num2, ope);
            numStack.push(result);
        }
        System.out.printf("表达式%s=%d\n", express, numStack.pop());
    }

    /**
     * 返回运算符的优先级 数字越大优先级越高
     */
    public static int priority(int ope) {
        if (ope == '*' || ope == '/') {
            return 1;
        } else if (ope == '+' || ope == '-') {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 判断是否是运算符
     *
     * @param c
     * @return
     */
    public static boolean isOpe(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public static int cal(int num1, int num2, int ope) {
        int result = 0;//计算的结果
        switch (ope) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num2 - num1;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num2 / num1;
                break;
        }
        return result;
    }
}
