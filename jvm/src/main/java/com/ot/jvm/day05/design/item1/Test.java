package com.ot.jvm.day05.design.item1;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public enum Test  {
    UNPAY("取消付款",1),PAID("已付款",2),TIMOUT("超时",1);

    private String desc;
    private Integer code;

    Test(String desc, Integer code) {
        this.desc = desc;
        this.code = code;
    }
}
