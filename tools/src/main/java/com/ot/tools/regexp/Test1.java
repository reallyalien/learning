package com.ot.tools.regexp;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test1 {

    @Test
    public void test1() {
        String content = "1995年，互联网的蓬勃发展给了Oak机会。业界为了使死板、单调的静态网页能够“灵活”起来，急需一种软件技术来开发" +
                "一种程序，这种程序可以通过网络传播并且能够跨平台运行。于是，世界各大IT企业为此纷纷投入了大量的人力、物力和财力。这" +
                "个时候，Sun公司想起了那个被搁置起来很久的Oak，并且重新审视了那个用软件编写的试验平台，由于它是按照嵌入式系统硬件平" +
                "台体系结构进行编写的，所以非常小，特别适用于网络上的传输系统，而Oak也是一种精简的语言，程序非常小，适合在网络上传" +
                "输。Sun公司首先推出了可以嵌入网页并且可以随同网页在网络上传输的Applet（Applet是一种将小程序嵌入到网页中进行执行的技术），并" +
                "将Oak更名为Java（在申请注册商标时，发现Oak已经被人使用了，再想了一系列名字之后，最终，使用了提议者在喝一杯Java咖啡时" +
                "无意提到的Java词语）。5月23日，Sun公司在Sun world会议上正式发布Java和HotJava浏览器。IBM、Apple、DEC、Adobe、HP、Oracle、" +
                "Netscape和微软等各大公司都纷纷停止了自己的相关开发项目，竞相购买了Java使用许可证，并为自己的产品开发了相应的Java平台。";

        //1.提取所有的英文单词
//        Pattern pattern = Pattern.compile("[a-zA-Z]+");
//        Pattern pattern = Pattern.compile("[0-9]+");
        Pattern pattern = Pattern.compile("([a-zA-Z]+)|([0-9]+)");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String group = matcher.group(0);
            System.out.println(group);
        }
    }

    @Test
    public void test2() {
        //如果匹配. 必须加 \\，否则会匹配所有字符
        String regex = "[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("abc$(a.bc(123(");
        while (matcher.find()) {
            System.out.println("找到：" + matcher.group(0));
        }
    }

    /**
     * 演示字符匹配符
     */
    @Test
    public void test3() {
        String content = "a11c8.abcABCy_";
        //如果匹配. 必须加 \\，否则会匹配所有字符
//        String regex = "[a-z]";//匹配a-z之间的任意一个字符
//        String regex = "[A-Z]";//匹配A-Z之间的任意一个字符
//        String regex = "abc";//默认区分大小写
//        String regex = "(?i)abc";//不区分大小写  case_insensitive 不区分大小写
//        String regex = "[^a-z]{2}";//不是a-z的字符，匹配2次
//        String regex = "[abcd]";//abcd中的任意一个字符
//        String regex = "\\w";//字母数字下划线
//        String regex = "\\s";//匹配空格
//        String regex = "\\S";//匹配非空格
        String regex = "\\.";//匹配空格
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        int n = 1;
        while (matcher.find()) {
            System.out.println((n++) + "-->" + matcher.group(0));
        }
    }

    /**
     * 演示选择匹配符
     */
    @Test
    public void test4() {
        String content = "han 韩 寒冷";
        String regex = "han|韩|寒"; //相当于或者
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        int n = 1;
        while (matcher.find()) {
            System.out.println((n++) + "-->" + matcher.group(0));
        }
    }

    /**
     * 演示限定符符
     */
    @Test
    public void test5() {
        String content = "11111aaaaaahelllo-";
//        String regex = "a{3}"; //表示查找aaa
//        String regex = "1{4}"; //表示查找1111
//        String regex = "\\d{2}"; //表示2位的数字字符
//        String regex = "a{2,5}"; //表示2位的数字字符
//        String regex = "a{3,4}"; //表示2位的数字字符
//        String regex = "1*"; //表示2位的数字字符
//        String regex = "a1?"; //表示2位的数字字符
        String regex = ""; //表示2位的数字字符
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        int n = 1;
        while (matcher.find()) {
            System.out.println((n++) + "-->" + matcher.group(0));
        }
    }

    /**
     * 演示定位符
     */
    @Test
    public void test6() {
        String content = "123 abc";
//        String regex = "^[0-9]+\\-[a-z]+$"; //
        String regex = "a\\b"; //
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        int n = 1;
        while (matcher.find()) {
            System.out.println((n++) + "-->" + matcher.group(0));
        }
    }

    /**
     * 演示分组
     */
    @Test
    public void test7() {
        String content = "hanhelloworld s7789 nn1189han";
        //非命名分组
//        String regex = "(\\d{2})(\\d)(\\d)";
        //命名分组
        String regex = "(?<g1>\\d{2})(?<g2>\\d)(\\d)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        int n = 1;
        while (matcher.find()) {
            System.out.println((n++) + "-->" + matcher.group(0));
            System.out.println("1分组" + "-->" + matcher.group("g1"));
            System.out.println("2分组" + "-->" + matcher.group("g2"));
            System.out.println("3分组" + "-->" + matcher.group(3));
            System.out.println("====================================");
        }
    }

    /**
     * 演示非捕获分组
     */
    @Test
    public void test8() {
        String content = "hello老胡教育 jack老胡老师 老胡同学hello";
//        String regex = "老胡(?:教育|老师|同学)";
        String regex = "老胡(?:教育|老师)"; //=只留下老胡，而不加后面的,:会把后面的也带上
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        int n = 1;
        while (matcher.find()) {
            System.out.println((n++) + "-->" + matcher.group(0));
            System.out.println((n++) + "-->" + matcher.group(1));
        }
    }

    /**
     * 演示非捕获分组
     */
    @Test
    public void test9() {
        String content = "Windows2000";
        String regex = "Windows(?!95|98|NT|2000)";//这个只会匹配windows
//        String regex = "Windows(95|98|NT|2000)$"; //两者的效果不一样，会匹配全部
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        int n = 1;
        while (matcher.find()) {
            System.out.println((n++) + "-->" + matcher.group(0));
        }
    }

    /**
     * 非贪婪匹配 ?用在限定符之后，非贪婪匹配
     */
    @Test
    public void test10() {
        String content = "hello1111";
//        String regex = "\\d+"; //贪婪匹配
        String regex = "\\d+?"; //非贪婪匹配，尽可能匹配少的
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        int n = 1;
        while (matcher.find()) {
            System.out.println((n++) + "-->" + matcher.group(0));
        }
    }

    /**
     * 非贪婪匹配 ?用在限定符之后，非贪婪匹配
     */
    @Test
    public void test11() {
        String content = "hello///1111";
        String regex = "/+?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        int n = 1;
        while (matcher.find()) {
            System.out.println((n++) + "-->" + matcher.group(0));
        }
    }

    @Test
    public void test12() {
        String content = "12ac";
        String regex = "[a|\\d]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

}
