package com.ot.mybatis;

import com.ot.mybatis.dao.AccountDao;
import com.ot.mybatis.domain.Account;
import com.ot.mybatis.util.BaseJunit;
import org.junit.Test;

import java.util.List;

public class AccountTest extends BaseJunit {

    @Test
    public void findAccountWithUser(){
        AccountDao mapper = sqlSession.getMapper(AccountDao.class);
        List<Account> list = mapper.findAccountWithUser();
        for (Account account : list) {
            System.out.println(account.toString());
        }
    }
}
