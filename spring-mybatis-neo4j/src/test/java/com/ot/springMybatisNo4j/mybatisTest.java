package com.ot.springMybatisNo4j;

import com.ot.springMybatisNeo4j.dao.AccountDao;
import com.ot.springMybatisNeo4j.dao.DeptDao;
import com.ot.springMybatisNeo4j.domain.Account;
import com.ot.springMybatisNeo4j.domain.Dept;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class mybatisTest {

    @Test
    public void findAll() throws IOException {
        //1.加载mybatis的主配置文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        //2.创建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //3.创建sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //4.执行操作
        AccountDao mapper = sqlSession.getMapper(AccountDao.class);
        DeptDao mapper1 = sqlSession.getMapper(DeptDao.class);
        List<Account> all = mapper.findAll();
        for (Account account : all) {
            System.out.println(account);
        }
        List<Dept> all1 = mapper1.findAll();
        //5.提交事务
        sqlSession.commit();
        //6.关闭资源
        sqlSession.close();
    }
}
