package com.ot.springMybatisNo4j;

import com.ot.springMybatisNeo4j.config.SpringConfig;
import com.ot.springMybatisNeo4j.dao.AccountDao;
import com.ot.springMybatisNeo4j.dao.DeptDao;
import com.ot.springMybatisNeo4j.domain.Account;
import com.ot.springMybatisNeo4j.domain.Dept;
import com.ot.springMybatisNeo4j.service.AccountService;
import com.ot.springMybatisNeo4j.service.DeptService;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class SpringTest {

    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * AbstractAutowireCapableBeanFactory 初始化Bean
     * 代理对象是在BeanPostProcess当中生成代理对象的
     *
     * AbstractAutowireCapableBeanFactory initBean 1773行
     */
    @Test
    public void springTest() {
        /**
         *  spring IOC容器当中的BeanDefinitionMap在实例化所有单例对象之前，map当中已经存在dao包下对象的BeanDefinition信息，bean的class信息为MapperFactoryBean
         */
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
        //在ioc容器初始化结束之后，accountServiceImpl已经是代理对象，代理对象的生成是在BeanPostProcess当中完成的，
        //而mybatis的dao对象，此时底层还是spring管理的MapperFactoryBean对象的实例（是一个factoryBean），mybatis代理对象尚未生成
        //上面的话有误，在beanFactory当中的SingletonObject确实是MapperFactoryBean实例，但是确实已经生成了mybatis的代理对象,
        //在进行注入的时候，AutoWire注解根据类型注入，我们知道对应FactoryBean，加&符号获取的是FactoryBean本身，我们注入的时候是不带&符号的
        //因此注入的是MapperFactoryBean的getObject()方法获取的mapper代理对象，故而mybatis和spring整合在了一起
        //在实例化MapperFactoryBean的时候，它实现了InitializingBean接口，回调afterPropertiesSet方法，会填充父类SqlSessionDaoSupport的sqlSessionTemplate属性
        //而此属性又会包含一个selSession的代理对象，代理对象的拦截器是SqlSessionInterceptor类
        AccountService accountService = ac.getBean(AccountService.class);
//        Object bean = ac.getBean("&accountDao");
//        DeptService deptService = ac.getBean(DeptService.class);
        List<Account> all = accountService.findAll();
//        List<Dept> all1 = deptService.findAll();
//        AccountDao bean = ac.getBean(AccountDao.class);
//        DeptDao DeptDao = ac.getBean(DeptDao.class);
//        List<Account> all = bean.findAll();
//        DeptDao.findAll();
        //执行findAll方法，先代理到MapperProxy的invoke方法，进而会调用到SqlSessionTemplate的selectList方法，通过sqlSessionTemplate内部持有的代理
        //对象sqlSessionProxy对象，再次进入SqlSessionInterceptor的invoke方法，调用事务同步器通过DefaultSessionFactory获取sqlSession,此时的sqlSession、
        //才是真正操作数据库的对象，因此在一个事务当中，只有一个sqlSession
        //然后才真正执行sqlSessionProxy的selectList方法,最终执行DefaultSqlSession的selectList方法,其中会调用springManagerTransaction的获取连接的方法
        //连接被事务同步器所管理
//        System.out.println(all);
    }

    @Test
    public void save() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
        AccountService accountService = ac.getBean(AccountService.class);
        Account account = new Account();
        account.setName("jjj");
        account.setMoney(70D);
        accountService.save(account);

//        Dept dept=new Dept();
//        dept.setName("jjj");
//        deptService.save(dept);
    }

    @Test
    public void transaction() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
        AccountService accountService = ac.getBean(AccountService.class);
        accountService.transaction();
    }
}
