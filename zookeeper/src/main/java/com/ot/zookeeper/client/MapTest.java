package com.ot.zookeeper.client;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", null);
        map.put("2", "ada");
        //此api如果指定k的值为null会进行替换，否则不作为
        map.computeIfAbsent("1", s -> s);
        //此api如果指定的key的值不为null，则将key和oldValue作为参数传入后面的lambda表达式
        map.computeIfPresent("2", ((s, s2) -> s + s2));
        System.out.println(map);
    }
}
