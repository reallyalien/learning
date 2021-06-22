package com.ot.line.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 中缀转后缀表达式
 * <p>
 * 1+（（2+3）*4）-5   --> 1 2 3 + 4 * + 5  =  16
 * 1 + ( 2 + 3 ) * 4  --> 1 2 3 + 4 * +    =  2 1
 */


/**
 * 中缀表达式转后缀表达式主要用到了栈进行运算符处理，队列进行排序输出，规则为：
 * <p>
 * 1.数字直接入队列
 * 2.运算符要与栈顶元素比较
 *  ①栈为空直接入栈
 *  ②运算符优先级大于栈顶元素优先级则直接入栈
 *  ③小于或等于则出栈入列，再与栈顶元素进行比较，直到运算符优先级小于栈顶元
 *     素优先级后，操作符再入栈
 * 因为在后缀表达式当中遇到符号顺序计算，必然运算符高的得排在前面，运算符高的得先计算
 * 3.操作符是 ( 则无条件入栈
 * 4.操作符为 )，则依次出栈入列，直到匹配到第一个(为止，此操作符直接舍弃，(直接出栈舍弃
 */
public class MiddleToPostfixExpression {

    public static void main(String[] args) {
//        String str = "1 + ( ( 2 + 3 ) * 4 ) - 5";
        String str = "1 + ( 2 + 3 ) * 4 ";
        List<String> list = Arrays.asList(str.split(" "));
        Stack<String> s1 = new Stack<>();//符号栈
//        Stack<String> s2 = new Stack<>();//存储中间结果的栈，存放操作数
        List<String> s2 = new ArrayList<>();//结果就不需要倒序排列了
        for (String s : list) {
            if (s.matches("\\d")) { //如果是数字
                s2.add(s);
            } else if (isOpe(s)) {//如果是运算符
                if (s1.isEmpty()) {
                    //如果符号栈为空，直接入栈
                    s1.push(s);
                } else {
                    //比较s与符号栈顶部元素的优先级
                    String peek = s1.peek();
                    if (leftOrRightBrackets(peek) == 1) {
                        //如果栈顶元素是左括号，直接入栈，这里不可能是右括号,因为我们压根就没有往里面放右括号
                        s1.push(s);
                    } else {//不是1就只能是0了
                        //比较运算符的高低，在符号栈当中，因该是运算符高的在上面，因此这里运算符低于栈顶元素所以栈顶元素出栈到队列
                        while (s1.size() > 0 && priority(s) <= priority(s1.peek())) {
                            s2.add(s1.pop());
                        }
                        s1.push(s);
                    }
                }
            } else {
                if (leftOrRightBrackets(s) == 1) {//左括号直接入栈
                    s1.push(s);
                } else if (leftOrRightBrackets(s) == 2) {//右括号，依次弹出s1栈顶的元素压入s2,直到遇到左括号为止，然后将这对括号丢弃
                    while (leftOrRightBrackets(s1.peek()) != 1) {
                        String pop = s1.pop();
                        s2.add(pop);
                    }
                    //如果碰到右括号，必然之前存在左括号，上面的while循环停止，接下来的一个就是左括号，弹出
                    s1.pop();
                }
            }
        }
        //将s1中剩余的运算符弹出加入s2
        while (s1.size() > 0) {
            s2.add(s1.pop());
        }
        String result = "";
        for (String s : s2) {
            result += s + " ";
        }
        System.out.println(result);
    }

    /**
     * 这里区分左括号还是右括号
     *
     * @param s
     * @return
     */
    public static int leftOrRightBrackets(String s) {
        if (s.equals("(")) {
            return 1;
        } else if (s.equals(")")) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * 区分是不是运算符
     *
     * @param c
     * @return
     */
    public static boolean isOpe(String c) {
        return c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/");
    }

    /**
     * 返回运算符的优先级 数字越大优先级越高
     */
    public static int priority(String ope) {
        if (ope.equals("*") || ope.equals("/")) {
            return 2;
        } else if (ope.equals("+") || ope.equals("-")) {
            return 1;
        } else {
            return 0;
        }
    }
}
