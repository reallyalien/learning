package com.ot.springMybatisNeo4j.service;

import com.ot.springMybatisNeo4j.dao.DeptDao;
import com.ot.springMybatisNeo4j.domain.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;

    @Override
    @Transactional
    public List<Dept> findAll() {
        return deptDao.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Dept dept) {
        deptDao.save(dept);

    }
}
