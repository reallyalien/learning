package com.ot.tools.regexp;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 应用实例
 */
public class Test2 {

    /**
     * 验证是否是一个汉字
     */
    @Test
    public void test1() {
        String content = "学校阿达";
        String reg = "^[\u0391-\uffe5]+$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("不满足");
        }
    }

    /**
     * 验证是否是一个邮政编码 1-9开头的6位数
     */
    @Test
    public void test2() {
        String content = "123456";
        String reg = "^[1-9]\\d{5}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("不满足");
        }
    }

    /**
     * 验证是否是一个qq号
     * 1-9开头的5-10位数
     */
    @Test
    public void test3() {
        String content = "123451";
        String reg = "^[1-9]\\d{4,9}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("不满足");
        }
    }

    /**
     * 验证是否是一个手机号
     * 必须以13 14 15 18 开头的11位数
     */
    @Test
    public void test4() {
        String content = "15313061739";
        String reg = "^1([3458])\\d{9}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("不满足");
        }
    }

    /**
     * 验证是否是一个url
     */
    @Test
    public void test6() {
        //特殊字符写在中括号当中无需转义
        String content = "https://www.bilibili.com/video/BV1Eq4y1E79W?p=17&spm_id_from=pageDriver";
        String reg = "^(http(s)?://)([\\w-]+\\.)+[\\w-]+(\\/[\\w-?=&/%.]*)?$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("不满足");
        }
    }
    /**
     * 验证是否是一个url
     */
    @Test
    public void test7() {
        //特殊字符写在中括号当中无需转义
        String content = "hello/world";
        String reg = "\\/";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        } else {
            System.out.println("不满足");
        }
    }

}
