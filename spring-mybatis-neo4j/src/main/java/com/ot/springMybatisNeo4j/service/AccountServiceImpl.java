package com.ot.springMybatisNeo4j.service;

import com.ot.springMybatisNeo4j.dao.AccountDao;
import com.ot.springMybatisNeo4j.dao.DeptDao;
import com.ot.springMybatisNeo4j.domain.Account;
import com.ot.springMybatisNeo4j.domain.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private DeptService deptService;

    @Transactional
    @Override
    public void transaction(){
        Account account = new Account();
        account.setMoney(90d);
        account.setName("jjjjj");
        accountService.save(account);

        Dept dept = new Dept();
        dept.setName("aaaa");
        deptService.save(dept);

    }
    @Override
    public Account findById(Integer id) {
        return null;
    }

    @Override
    @Transactional
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    @Transactional
    public void save(Account account) {
        accountDao.save(account);
//        Dept dept = new Dept();
//        dept.setName("aaaa");
//       deptDao.save(dept);
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
