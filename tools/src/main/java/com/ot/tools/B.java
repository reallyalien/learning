package com.ot.tools;

import com.ot.tools.faker.User;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class B {


    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        list.add(new User("a", 10));
        list.add(new User("b", 20));
        list.add(new User("c", 30));
        a(list);
        System.out.println(list);
        System.out.println(BigDecimal.ZERO);
    }


    public static void a(List<User> users) {
        User user = users.get(0);
        user.setAddress("aaaaaaaa");
    }
}
