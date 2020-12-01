package com.ot.springMybatisNo4j;

import com.ot.springMybatisNeo4j.config.SpringConfig;
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

    @Test
    public void springTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
        AccountService accountService = ac.getBean(AccountService.class);
        DeptService deptService = ac.getBean(DeptService.class);
        List<Account> all =
                accountService.findAll();
        List<Dept> all1 = deptService.findAll();
        System.out.println(all);
    }
    @Test
    public void save(){
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
    public void transaction(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
        AccountService accountService = ac.getBean(AccountService.class);
        accountService.transaction();
    }
}
