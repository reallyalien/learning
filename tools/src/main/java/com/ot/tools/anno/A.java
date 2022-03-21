package com.ot.tools.anno;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class A {

    public static void main(String[] args) {
        Human humanA = new Human("a",10,null,null,null,"2021-10-11");
        Human humanB = new Human("b",11,null,null,null,"2021-10-12");
        Human humanC = new Human("c",12,null,null,null,"2021-10-13");
        List<Human> humans=new ArrayList<>();
        humans.add(humanA);
        humans.add(humanB);
        humans.add(humanC);
        List<Human> collect = humans.stream().sorted(Comparator.comparing(Human::getCreateTime).reversed()).collect(Collectors.toList());
        System.out.println(collect);
    }
}
