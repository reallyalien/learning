package com.ot.springdbutils.dao;

import com.ot.springdbutils.domain.Account;

import java.util.List;

public interface AccountDao {

    public Account findById(Integer id);

    public List<Account> findAll();

    public void save(Account account);

    public void update(Account account);

    public void delete(Integer id);

    public Account findByName(String name);

    public void updateByName(Account account);
}
