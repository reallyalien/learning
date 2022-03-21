package com.ot.tools.anno;

import lombok.Builder;
import lombok.Data;

@Anno2(value = "222")
@Data
@Builder
public class Human {

    private String name;
    private int age;
    private String country;
    private String province;
    private String city;

    private String createTime;
    static {
        System.out.println("aaa");
    }
}
