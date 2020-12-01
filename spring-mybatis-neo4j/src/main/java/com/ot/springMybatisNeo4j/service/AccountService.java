package com.ot.springMybatisNeo4j.service;

import com.ot.springMybatisNeo4j.domain.Account;

import java.util.List;

public interface AccountService {

    public Account findById(Integer id);

    public List<Account> findAll();

    public void save(Account account);

    public void update(Account account);

    public void delete(Integer id);

    public Account findByName(String name);

    public void updateByName(Account account);

    void transaction();
}
