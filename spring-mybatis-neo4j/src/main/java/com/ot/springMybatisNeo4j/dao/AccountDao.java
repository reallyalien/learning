package com.ot.springMybatisNeo4j.dao;


import com.ot.springMybatisNeo4j.domain.Account;

import java.util.List;

public interface AccountDao {

    public List<Account> findAll();

    public void save(Account account);

}
