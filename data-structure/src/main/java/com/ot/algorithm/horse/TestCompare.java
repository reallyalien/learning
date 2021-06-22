package com.ot.algorithm.horse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestCompare {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 8, 10, 3);
        list.sort(((o1, o2) -> o2 - o1));
        System.out.println(list);
    }
}
