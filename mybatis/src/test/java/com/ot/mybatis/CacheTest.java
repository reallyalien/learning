package com.ot.mybatis;

import com.ot.mybatis.dao.AccountDao;
import com.ot.mybatis.dao.UserDao;
import com.ot.mybatis.domain.Account;
import com.ot.mybatis.domain.User;
import com.ot.mybatis.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class CacheTest {

    /**
     * 一级缓存默认开启，增删改操作默认删除缓存，
     */
    @Test
    public void cache() {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user1 = mapper.findById(1L);
        sqlSession.close();

        SqlSession sqlSession2 = SqlSessionUtil.getSqlSession();
        UserDao mapper2 = sqlSession2.getMapper(UserDao.class);
        User user2 = mapper2.findById(1L);
        System.out.println(user1 == user2);
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
        System.out.println(user1 == user2);
    }

    /**
     * https://www.cnblogs.com/happyflyingpig/p/7739749.html
     * <p>
     * 只有同一个sqlSession操作才会取缓存，在与spring整合之后，dao接口每调用一次方法都会打开一个新的sqlSession，因此一级缓存已经失效，
     * 除非在一个事务当中才会取的是同一个selSession，sqlSession不能关闭，关闭缓存对象就置为null,一级缓存才能生效，然后二级缓存需要在xml配置文件当中配置，默认使用的是LRUCache，包装了
     * 一个 delegate 对象，其实就是一个map，此缓存对象拥有一个唯一的id，就是映射文件的namespace，多个映射文件可以通过cache-ref引用同一个cache
     * 这样的话，mybatis就不需要为每一个开启缓存的映射文件都创建一个新对象，当设置了更新间隔之后会,层层代理，与插件机制是一样的，只不过
     * ScheduledCache->LruCache->PerpetualCache-cache    插件是jdk动态代理，代理对象代理上一次代理对象，这里是对象包装对象，
     */
    @Test
    public void cache3() {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user1 = mapper.findById(1L);
        User user2 = mapper.findById(1L);
        System.out.println(user1 == user2);
    }

    @Test
    public void cache4() {
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        AccountDao mapper1 = sqlSession.getMapper(AccountDao.class);
        User user1 = mapper.findById(1L);
        List<Account> accountWithUser = mapper1.findAccountWithUser();
//        System.out.println(user1==user2);
    }
}
