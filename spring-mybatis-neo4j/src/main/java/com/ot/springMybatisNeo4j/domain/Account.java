package com.ot.springMybatisNeo4j.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Account {

    private Integer id;
    private String name;
    private Double money;
    private Date createTime;
}
