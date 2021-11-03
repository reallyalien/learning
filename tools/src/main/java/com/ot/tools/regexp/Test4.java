package com.ot.tools.regexp;

import com.google.inject.internal.cglib.core.$ProcessArrayCallback;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分组，捕获，反向引用
 */
public class Test4 {

    /**
     * 4位数，比如1221，6886
     */
    @Test
    public void test1() {
        String content = "12123hello8998aaa";
        String reg = "(\\d)(\\d)\\2";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println("===");
        }
    }

    /**
     * 在字符串当中检索商品编号，形式如：12321-333999111 这样的号码，要求满足前面是一个五位数，然后是-然后是一个九位数，连续的3位相同
     */
    @Test
    public void test2() {
        String content = "12321-333999111";
        String reg = "\\d{5}-(\\d)\\1{2}(\\d)\\2{2}(\\d)\\3{2}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
        }
    }

    /**
     * 我....我要....学学学学....编程java
     */
    @Test
    public void test3() {
        String content = "我....我要....学学学学....编程java";
        //先去掉.
        Pattern pattern = Pattern.compile("\\.");
        Matcher matcher = pattern.matcher(content);
//        //获取
        pattern = Pattern.compile("(.)\\1+");
        matcher = pattern.matcher(content);
        //使用方向引用替换,在正则表达式外部使用$,用我来替换我我，学替换学学学学
        String s = matcher.replaceAll("$1");
        System.out.println(s);
//        String $1 = Pattern.compile("(.)\\1+").matcher(content).replaceAll("$1");
//        System.out.println($1);
    }

    /**
     * 我....我要....学学学学....编程java
     */
    @Test
    public void test4() {
        String content = "我....我要....学学学学....编程java";
        Pattern pattern = Pattern.compile("\\.");
        Matcher matcher = pattern.matcher(content);
        String s = matcher.replaceAll("");
        System.out.println("s :->" + s);
        Pattern pattern1 = Pattern.compile("([\u0391-\uffe5])\\1+");
        Matcher matcher1 = pattern1.matcher(s);
        //用它自己来替换整体,
        String $1 = matcher1.replaceAll("$1");
        System.out.println("$1:->" + $1);

    }
}
