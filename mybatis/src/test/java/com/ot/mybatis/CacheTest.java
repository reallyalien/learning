package com.ot.mybatis;

import com.ot.mybatis.dao.UserDao;
import com.ot.mybatis.domain.User;
import com.ot.mybatis.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class CacheTest {

    /**
     * 一级缓存默认开启，增删改操作默认删除缓存，
     */
    @Test
    public void cache() {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user1 = mapper.findById(46L);
        sqlSession.close();

        SqlSession sqlSession2 = SqlSessionUtil.getSqlSession();
        UserDao mapper2 = sqlSession2.getMapper(UserDao.class);
        User user2 = mapper2.findById(46L);
        System.out.println(user1==user2);
    }

    /**
     * 验证二级缓存 ,在开启二级缓存的情况下，当sqlSession关闭之后，再次查询也不会重新去查询数据库，而是从当前二级缓存当中去拿
     * 意味着多个sqlSession可以同时使用二级缓存，也就是多个线程操作可以同时使用二级缓存
     * 二级缓存在增删改操作的时候会清空,可以手动清空
     */
    @Test
    public void cache2() {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user1 = mapper.findById(46L);
        sqlSession.close();
        SqlSession sqlSession2 = SqlSessionUtil.getSqlSession();
        UserDao mapper2 = sqlSession2.getMapper(UserDao.class);
        User user2 = mapper2.findById(46L);
        System.out.println(user1==user2);
    }
}
