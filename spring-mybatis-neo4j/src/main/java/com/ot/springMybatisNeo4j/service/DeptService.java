package com.ot.springMybatisNeo4j.service;

import com.ot.springMybatisNeo4j.domain.Dept;

import java.util.List;

public interface DeptService {

    public List<Dept> findAll();

    public void save(Dept account);
}
