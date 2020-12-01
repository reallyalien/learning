package com.ot.springdbutils.dao.impl;

import com.ot.springdbutils.dao.AccountDao;
import com.ot.springdbutils.domain.Account;
import com.ot.springdbutils.utils.ConnectionUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {


    @Autowired
    private QueryRunner queryRunner;

    @Autowired
    private ConnectionUtil connectionUtil;

    @Override
    public Account findById(Integer id) {
        Account account = null;
        try {
            account = queryRunner.query(connectionUtil.getConnection(), "select * from account where id = ?", new BeanHandler<>(Account.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = null;
        try {
            accounts = queryRunner.query(connectionUtil.getConnection(), "select * from account", new BeanListHandler<>(Account.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void save(Account account) {
        try {
            queryRunner.update(connectionUtil.getConnection(), "insert into account (name,money) values (?,?)", account.getName(), account.getMoney());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Account account) {
        try {
            queryRunner.update(connectionUtil.getConnection(), "update account set name=? ,money=? where id = ? ", account.getName(), account.getMoney(), account.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            queryRunner.update(connectionUtil.getConnection(), "delete account  where id = ? ", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account findByName(String name) {
        Account account = null;
        try {
            account = queryRunner.query(connectionUtil.getConnection(), "select * from account where name = ?", new BeanHandler<>(Account.class), name);
            System.out.println(connectionUtil.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public void updateByName(Account account) {
        try {
            queryRunner.update(connectionUtil.getConnection(), "update account set money = ? where name = ?", account.getMoney(), account.getName());
            System.out.println(connectionUtil.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
