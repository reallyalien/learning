package com.ot.mybatis.dao;

import com.ot.mybatis.domain.Account;

import java.util.List;

public interface AccountDao {

    /**
     * 账户到用户的一对一关系
     * @return
     */
    List<Account> findAccountWithUser();

}
