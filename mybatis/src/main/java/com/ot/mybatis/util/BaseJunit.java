package com.ot.mybatis.util;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;

public class BaseJunit {

    protected SqlSession sqlSession;

    @Before
    public void before() {
        sqlSession = SqlSessionUtil.getSqlSession();
    }

    @After
    public void after() {
        sqlSession.commit();
        sqlSession.close();
    }
}
