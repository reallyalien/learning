package com.ot.tools.jdbc;

import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.UUID;

public class A {

    public static void main(String[] args) {
        String text = "{'a':'b'}";
        Map map = JSON.parseObject(text, Map.class);
        System.out.println(map);

        String s = UUID.randomUUID().toString().replaceAll("-", "_");
        System.out.println(s);
    }
}
