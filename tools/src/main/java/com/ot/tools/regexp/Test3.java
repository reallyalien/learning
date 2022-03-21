package com.ot.tools.regexp;

import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test3 {

    /**
     * 用于整体匹配
     */
    @Test
    public void test1() {
        String content = "hello abc hello";
        String reg = "hello.*";
        boolean matches = Pattern.matches(reg, content);
        System.out.println(matches);

    }

    /**
     * match的常用方法
     */
    @Test
    public void test2() {
        String content = "hello edu jack tom hello smith hello";
        String reg = "hello.";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("==================");
            System.out.println("start:" + matcher.start());
            System.out.println("end:" + matcher.end());
            System.out.println("find:" + content.substring(matcher.start(), matcher.end()));
        }
        //整体匹配，
        System.out.println("整体匹配开始");
        boolean matches = matcher.matches();
        System.out.println();
        System.out.println(matches);

    }

    /**
     * match的常用方法
     */
    @Test
    public void test3() {
        String content = "hello edu jack tom hello smith hello";
        String reg = "hello";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        //返回的字符串才是替换后的字符串，原先的content是不会变化的
        String hello = matcher.replaceAll("世界");

        System.out.println(hello);
    }

    @Test
    public void test4() {
//        Object[] obj=new Object[]{"abc","opy","abd","iop"};
//        Arrays.sort(obj);
//        System.out.println(Arrays.toString(obj));
        StringBuilder sb = new StringBuilder("1313131313131&");
        String substring = sb.substring(0, sb.length() - 1);
        System.out.println(substring);
    }
}
