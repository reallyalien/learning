package com.ot.springMybatisNeo4j.dao;

import com.ot.springMybatisNeo4j.domain.Account;
import com.ot.springMybatisNeo4j.domain.Dept;

import java.util.List;

public interface DeptDao {
    public List<Dept> findAll();

    public void save(Dept account);

}
