package com.ot.mybatis;

import com.ot.mybatis.dao.AccountDao;
import com.ot.mybatis.dao.UserDao;
import com.ot.mybatis.dao.impl.UserDaoImpl;
import com.ot.mybatis.domain.QueryDto;
import com.ot.mybatis.domain.User;
import com.ot.mybatis.util.BaseJunit;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserTest extends BaseJunit {

    @Test
    public void findAll() throws IOException {
        //1.加载mybatis的主配置文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        //2.创建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //3.创建sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //4.执行操作
        List<Object> list = sqlSession.selectList("com.ot.mybatis.dao.UserDao.findAll");
        //5.提交事务
        sqlSession.commit();
        //6.关闭资源
        sqlSession.close();
    }

    @Test
    public void findAll_1() throws IOException {
        UserDaoImpl userDao = new UserDaoImpl();
        List<User> all = userDao.findAll();
    }

    @Test
    public void findAll_2() throws IOException {
        //1.加载mybatis的主配置文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        //2.创建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //3.创建sqlSession|DefaultSqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //4.执行操作
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        AccountDao accountDao = sqlSession.getMapper(AccountDao.class);
        userDao.findAll();
        //5.提交事务
        sqlSession.commit();
        //6.关闭资源
        sqlSession.close();
    }

    @Test
    public void findAll_3() throws IOException {
        //基于jdk动态代理生成实现
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> all = userDao.findAll();
    }

    @Test
    public void findByName() throws IOException {
        //基于jdk动态代理生成实现
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> all = userDao.findByName("小");
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void findByName1() throws IOException {
        //基于jdk动态代理生成实现
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> all = userDao.findByName1("小");
        for (User user : all) {
            System.out.println(user);
        }
    }

    @Test
    public void save() throws IOException {
        //基于jdk动态代理生成实现
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setAddress("xxxx");
        user.setBirthday(new Date());
        user.setUsername("猪猪");
        user.setSex("男");
        Long save = userDao.save(user);
        System.out.println(save);
        System.out.println(user.getId());
    }

    @Test
    public void findByIdAndUserName() throws IOException {
        //基于jdk动态代理生成实现
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = new User();
        user.setUsername("猪猪");
        user.setId(0L);
        List<User> list = userDao.findByIdAndUserName(user);
        System.out.println(list);
    }

    @Test
    public void findByIdAndUserNameChoose() throws IOException {
        //基于jdk动态代理生成实现
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = new User();
//        user.setUsername("猪猪");
//        user.setId(81L);
        List<User> list = userDao.findByIdAndUserNameChoose(user);
        System.out.println(list);
    }

    @Test
    public void findByIds1() throws IOException {
        //基于jdk动态代理生成实现
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<Long> ids = new ArrayList<>();
        ids.add(70L);
        ids.add(80L);
        ids.add(42L);
        List<User> list = userDao.findByIds1(ids);
        System.out.println(list);
    }
    @Test
    public void findByIds2() throws IOException {
        //基于jdk动态代理生成实现
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        Long[] ids = new Long[]{70L, 42L, 81L};
        List<User> list = userDao.findByIds2(ids);
        System.out.println(list);
    }

    @Test
    public void findByIds3() throws IOException {
        //基于jdk动态代理生成实现
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        QueryDto dto = new QueryDto();
        List<Long> ids = new ArrayList<>();
        ids.add(70L);
        ids.add(80L);
        ids.add(42L);
        dto.setIds(ids);
        List<User> list = userDao.findByIds3(dto);
        System.out.println(list);
    }
    @Test
    public void findUserAndAccount() throws IOException {
        //基于jdk动态代理生成实现
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> list = userDao.findUserAndAccount();
        for (User user : list) {
            System.out.println(user);
        }
    }


    @Test
    public void findUserAndAccountAndRole() throws IOException {
        //基于jdk动态代理生成实现
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> list = userDao.findUserAndAccountAndRole();
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void findAllByPage() throws IOException {
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        List<User> all = mapper.findAllByPage();
        for (User user : all) {
            System.out.println(user);
        }
    }
}
