package com.ot.springMybatisNeo4j.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.ot.springMybatisNeo4j")
public class SpringConfig implements TransactionManagementConfigurer {


    @Bean(name = "dataSource")
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/springday02");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sessionFactoryBean(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(StdOutImpl.class);
        sqlSessionFactoryBean.setConfiguration(configuration);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.ot.springMybatisNeo4j.dao");
        return mapperScannerConfigurer;
    }
    /**
     * 同一个controller不能去同时调几个service做数据库操作，事务无法控制
     * AccountService txObject@2884 com.mysql.jdbc.JDBC4Connection@6e0ff644       datasource:@2887 conHolder:@3389 sqlSession@3458
     * DeptService    txObject@3531 com.mysql.jdbc.JDBC4Connection@6e0ff644       datasource:@2887 conHolder:@3550 sqlSession@4578
     * 2个不同的service操作数据库，这个数据库连接不同
     */
    /**
     * 同一个service操作2个dao，connection'是一样的，生成代理的对象的时SqlSessionTemplate是不一样的，生成的代理对象也是不一样的
     * 真正操作数据库的是DefaultSqlSession,在一个事务当中只使用一个，其实就是connection在一个事务当中是一样的
     * 同一个service可以同时去操作其他的service，保证方法上用于注解@Transaction就可以保证用同一个connection,然后保证事务安全
     */

    /**
     * 在service层面是dataSource和connectionHolder绑定
     * 在mybatis操作层是DefaultSessionFactory与SqlSessionHolder绑定，绑定都使用的是spring的事务同步器 TransactionSynchronizationManager
     */
    /**
     *TransactionManagementConfigurer
     */
    /**
     * 默认的事务管理器在创建新连接的时候，就通过 TransactionSynchronizedManager 将datasource与connectionHolder绑定
     ThreadLocal set   2个线程同一个threadLocal对象，set值，获取当前线程的threadLocalMap，
     这个是不一样的，给不同的map设置值，key为threadLocal，值是map(key为datasource生成的，
     value是connectionHolder（对connection的包装）)
     *sqlsessionFactoryBean的320行：SpringManagedTransactionFactory：这个类当中，可以查询到当前的连接跟mybatis拿到的连接是同一个
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    /**
     * https://blog.csdn.net/u010963948/article/details/79208328
     * 其返回值代表在拥有多个事务管理器的情况下默认使用的事务管理器
     * @return
     */
    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return null;
    }

    //==========================================================================================================


}
