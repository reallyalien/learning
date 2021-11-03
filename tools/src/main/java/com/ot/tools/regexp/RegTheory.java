package com.ot.tools.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式原理
 */
public class RegTheory {
    public static void main(String[] args) {
        String content = "1992啊啊啊啊啊啊啊啊啊啊打打的阿达速度是8907尽快开始的6789klsds的撒都是90";
        //匹配所有4个数字的字符串
        String regex = "(\\d\\d)(\\d\\d)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        /**
         * match.find()
         * 1.根据指定的规则，定位满足规则的子字符串，比如找到1992
         * 2.找到后将子字符串的开始索引记录在match的属性当中 int[] groups;
         * group[0]=0，把该字符串的结束的索引+1记录下来group[1]=4
         * 3.同时记录oldLast为子字符串结束的索引+1
         *
         * match.group（0）:
         *   public String group(int group) {
         *         if (first < 0)
         *             throw new IllegalStateException("No match found");
         *         if (group < 0 || group > groupCount())
         *             throw new IndexOutOfBoundsException("No group " + group);
         *         if ((groups[group*2] == -1) || (groups[group*2+1] == -1))
         *             return null;
         *         return getSubSequence(groups[group * 2], groups[group * 2 + 1]).toString();
         *     }
         *   1.根据group数组截取字符串 group[0]-group[1] 也就是0-4的字符串
         *
         *   match.find()分组情况讨论,正则表达式当中含有(),第一个()表示第一组。。。
         *   1.第一个找到的是1995，记录group[0]=0,group[1]=4
         *   然后再记录第一组匹配到的字符串也就是19,group[2]=0,group[3]=2,
         *   接着记录第二组()98,group[4]=2,group[5]=4,如果有更多的分组，继续
         */

        while (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println("=======================");
        }

    }
}
