package com.ot.jvm.day05.method.item1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Demo11 {
    public static void main(String[] args) {
        System.out.println(mod(-1));
    }

    /**
     * vm参数 -ea开启断言,不开启不生效。
     * @param n
     * @return
     */
    public static int mod(int n){
        List list=new ArrayList();
        List emptyList = Collections.EMPTY_LIST;
        assert n > 0;
        return n;
    }
    public <T> void show(T t){
        System.out.println(t);
    }
}
