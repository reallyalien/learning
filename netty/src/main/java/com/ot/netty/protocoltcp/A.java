package com.ot.netty.protocoltcp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;

import java.util.LinkedHashMap;

public class A {

    public static void main(String[] args) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("a","b");
        String s = JSON.toJSONString(map);
        System.out.println(JSON.toJSONString(s));
    }
}
