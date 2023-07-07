package com.ot.tools.jdbc.common;


import cn.hutool.extra.pinyin.PinyinUtil;

import java.util.List;
import java.util.function.Consumer;

public class B {
    public static void main(String[] args) {
        String aaa = PinyinUtil.getPinyin("你好");
        String s = aaa.replaceAll(" ", "");
        System.out.println(s);
    }
}
