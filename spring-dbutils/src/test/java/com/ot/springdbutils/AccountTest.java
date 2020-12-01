package com.ot.springdbutils;

import com.ot.springdbutils.config.SpringConfig;
import com.ot.springdbutils.domain.Account;
import com.ot.springdbutils.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class AccountTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void findById0() {
        Account account = accountService.findById(1);
        System.out.println(account);
    }

    @Test
    public void findById() {
//        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("application.xml");
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);
        AccountService accountService = ac.getBean(AccountService.class);
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        Account account = accountService.findById(1);
        System.out.println(account);
    }

    @Test
    public void findAll() {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("application.xml");
        AccountService accountService = ac.getBean(AccountService.class);
        List<Account> all = accountService.findAll();
        for (Account account : all) {
            System.out.println(account);
        }
    }

    /**
     * 转账案例测试
     */
    @Test
    public void transAccounts() {
        String from = "a";
        String to = "b";
        Double money = 100D;
        accountService.transferAccounts(from, to, money);
    }

    /**
     * 转账案例测试(spring注解控制)
     * spring生成的connection与dao的connection根本不是同一个connection，因此事务无法控制
     */
    @Test
    public void transAccountSpring() {
        String from = "a";
        String to = "b";
        Double money = 100D;
        accountService.transferAccountsSpring(from,to,money);
    }
}
