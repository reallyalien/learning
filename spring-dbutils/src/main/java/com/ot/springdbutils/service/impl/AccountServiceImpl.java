package com.ot.springdbutils.service.impl;

import com.ot.springdbutils.dao.AccountDao;
import com.ot.springdbutils.domain.Account;
import com.ot.springdbutils.service.AccountService;
import com.ot.springdbutils.utils.TxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TxUtil txUtil;

    @Override
    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    public void update(Account account) {
        accountDao.save(account);
    }

    @Override
    public void delete(Integer id) {
        accountDao.delete(id);
    }

    /**
     * 转账案例
     *
     * @param from
     * @param to
     * @param money
     */
    @Override
    public void transferAccounts(String from, String to, Double money) {
        //查询2个账户，queryRunner每执行一次dao操作，就会创建一个connection，而connection默认是自动提交的，也就是说在这个方法当中
        //第一个update方法执行完成之后，事务已经提交，出现异常，如果我们不给queryRunner一个connection，这个方法相当于开了4个事务，而不是一个事务，根本无法控制
        try {
            txUtil.openTx();
            Account fromAccount = accountDao.findByName(from);
            Account toAccount = accountDao.findByName(to);
            //修改金额
            fromAccount.setMoney(fromAccount.getMoney() - money);
            toAccount.setMoney(toAccount.getMoney() + money);
            //更新金额
            accountDao.updateByName(fromAccount);
            //模拟异常
            int a = 1 / 0;
            accountDao.updateByName(toAccount);
            txUtil.commit();
        } catch (Exception e) {
            e.printStackTrace();
            txUtil.rollback();
        } finally {
            txUtil.close();
        }

    }

    /**
     * 1.原因是，Spring 默认通过动态代理的方式实现 AOP，对目标方法进行增强，private 方法无法代理到，
     * Spring 自然也无法动态增强事务处理逻辑。
     * 2.只有当异常从方法当中传播出去，并且是RunTimeException和Error才能进行回滚,编译时异常不会回滚
     */

    /**
     * 注解控制事务
     *
     * @param from
     * @param to
     * @param money
     */
    @Override
    @Transactional(transactionManager = "transactionManager",propagation = Propagation.REQUIRED)
    public void transferAccountsSpring(String from, String to, Double money) {
        Account fromAccount = accountDao.findByName(from);
        Account toAccount = accountDao.findByName(to);
        //修改金额
        fromAccount.setMoney(fromAccount.getMoney() - money);
        toAccount.setMoney(toAccount.getMoney() + money);
        //更新金额
        accountDao.updateByName(fromAccount);
        //模拟异常
        int a = 1 / 0;
        accountDao.updateByName(toAccount);
    }
}
