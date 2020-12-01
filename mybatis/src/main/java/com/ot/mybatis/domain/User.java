package com.ot.mybatis.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class User implements Serializable {

    private Long id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;
    private List<Account> accounts;
    private List<Role> roles;
}
