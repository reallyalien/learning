package com.ot.mybatis.dao.impl;

import com.ot.mybatis.dao.UserDao;
import com.ot.mybatis.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserDaoImpl {


    public List<User> findAll() throws IOException {
        //1.加载mybatis的主配置文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        //2.创建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //3.创建sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> list = sqlSession.selectList("com.ot.mybatis.dao.UserDao.findAll");
        sqlSession.commit();
        sqlSession.close();
        return list;
    }
}
