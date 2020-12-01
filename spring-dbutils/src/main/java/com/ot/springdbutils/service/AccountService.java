package com.ot.springdbutils.service;

import com.ot.springdbutils.domain.Account;

import java.util.List;

public interface AccountService {

    public Account findById(Integer id);

    public List<Account> findAll();

    public void save(Account account);

    public void update(Account account);

    public void delete(Integer id);

    public void transferAccounts(String from, String to, Double amount);

    public void transferAccountsSpring(String from, String to, Double amount);
}
