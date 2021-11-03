package com.ot.springMybatisNeo4j.service;

import com.ot.springMybatisNeo4j.dao.AccountDao;
import com.ot.springMybatisNeo4j.dao.DeptDao;
import com.ot.springMybatisNeo4j.domain.Account;
import com.ot.springMybatisNeo4j.domain.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private DeptDao deptDao;

    @Transactional
    @Override
    public void transaction() {
//        Account account = new Account();
//        account.setMoney(90d);
//        account.setName("jjjjj");
//
//        Dept dept = new Dept();
//        dept.setName("aaaa");

    }

    @Override
    public Account findById(Integer id) {
        return null;
    }

    @Override
    @Transactional
    public List<Account> findAll() {
        deptDao.findAll();
        System.out.println("11");
        accountDao.findAll();
        return null;
    }

    @Override
    @Transactional
    public void save(Account account) {
//        accountDao.save(account);
    }

    @Override
    public void update(Account account) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Account findByName(String name) {
        return null;
    }

    @Override
    public void updateByName(Account account) {

    }
}
