package com.ot.tools.util.util;


import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class A {

    private Map<String, String> map;

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public static void main(String[] args) {
        List<Cat> list = new ArrayList<>();
        list.add(new Cat());
//        Map<String, String> collect = list.stream().collect(Collectors.toMap(Cat::getName, Cat::getAge));
        List<String> collect1 = list.stream().map(e -> e.getAge()).collect(Collectors.toList());
        System.out.println(collect1.isEmpty());
        System.out.println(collect1.get(0));
    }
}
