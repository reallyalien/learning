package com.ot.tools.anno;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分组测试
 */
public class ListTest {
    public static void main(String[] args) {
        ArrayList<Human> list = new ArrayList<>();
        list.add(new Human.HumanBuilder().country("中国").province("山西").city("长治").age(10).name("a").build());
        list.add(new Human.HumanBuilder().country("中国").province("山西").city("长治").age(20).name("b").build());
        list.add(new Human.HumanBuilder().country("中国").province("陕西").city("西安").age(30).name("c").build());
        list.add(new Human.HumanBuilder().country("中国").province("山西").city("太原").age(40).name("d").build());
        Map<String, List<Human>> collect = list.stream().collect(
                Collectors.groupingBy(e -> e.getCountry() + "_" + e.getProvince() + "_" + e.getCity())
        );
        List<List<Human>> listList = collect.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        System.out.println();
    }
}
