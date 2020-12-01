package com.ot.mybatis.domain;

import lombok.Data;

@Data
public class Account {
    private Long id;
    private Double money;
    private User user;
}
