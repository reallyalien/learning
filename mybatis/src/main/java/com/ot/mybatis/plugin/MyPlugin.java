package com.ot.mybatis.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

/**
 * 根据当前的Interceptor上面的注解定义哪些接口需要拦截，然后判断当前目标对象是否有实现对应需要拦截的接口，
 * 如果没有则返回目标对象本身，如果有则返回一个代理对象。而这个代理对象的InvocationHandler正是一个Plugin。
 * 所以当目标对象在执行接口方法时，如果是通过代理对象执行的，则会调用对应InvocationHandler的invoke方法，
 * 也就是Plugin的invoke方法。所以接着我们来看一下该invoke方法的内容。这里invoke方法的逻辑是：如果当前执行
 * 的方法是定义好的需要拦截的方法，则把目标对象、要执行的方法以及方法参数封装成一个Invocation对象，再把封
 * 装好的Invocation作为参数传递给当前拦截器的intercept方法。如果不需要拦截，则直接调用当前的方法。Invocation
 * 中定义了定义了一个proceed方法，其逻辑就是调用当前方法，所以如果在intercept中需要继续调用当前方法的话可以
 * 调用invocation的procced方法。
 * <p>
 * 这就是Mybatis中实现Interceptor拦截的一个思想，如果用户觉得这个思想有问题或者不能完全满足你的要求的话可以
 * 通过实现自己的Plugin来决定什么时候需要代理什么时候需要拦截。以下讲解的内容都是基于Mybatis的默认实现即通过
 * Plugin来管理Interceptor来讲解的。
 * <p>
 * 对于实现自己的Interceptor而言有两个很重要的注解，一个是@Intercepts，其值是一个@Signature数组。@Intercepts用
 * 于表明当前的对象是一个Interceptor，而@Signature则表明要拦截的接口、方法以及对应的参数类型。来看一个自定义的简单Interceptor：
 * <p>
 * 代理模式，责任链模式，aop
 * <p>
 * 支持拦截的方法
 * 执行器Executor（update、query、commit、rollback等方法）；
 * 参数处理器ParameterHandler（getParameterObject、setParameters方法）；
 * 结果集处理器ResultSetHandler（handleResultSets、handleOutputParameters等方法）；
 * SQL语法构建器StatementHandler（prepare、parameterize、batch、update、query等方法）；
 * <p>
 * <p>
 * 代理模式，责任链模式，aop
 * <p>
 * 支持拦截的方法
 * 执行器Executor（update、query、commit、rollback等方法）；
 * 参数处理器ParameterHandler（getParameterObject、setParameters方法）；
 * 结果集处理器ResultSetHandler（handleResultSets、handleOutputParameters等方法）；
 * SQL语法构建器StatementHandler（prepare、parameterize、batch、update、query等方法）；
 * <p>
 */
/**
 * 代理模式，责任链模式，aop
 * <p>
 * 支持拦截的方法
 * 执行器Executor（update、query、commit、rollback等方法）；
 * 参数处理器ParameterHandler（getParameterObject、setParameters方法）；
 * 结果集处理器ResultSetHandler（handleResultSets、handleOutputParameters等方法）；
 * SQL语法构建器StatementHandler（prepare、parameterize、batch、update、query等方法）；
 * <p>
 */

/**
 * statementHandler拦截，分页操作
 */
@Intercepts(@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}))
public class MyPlugin implements Interceptor {

    //获取xml当中的配置信息
    private String startIndex;
    private String pageSize;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //对于StatementHandler其实只有两个实现类，一个是RoutingStatementHandler，另一个是抽象类BaseStatementHandler，
        //BaseStatementHandler有三个子类，分别是SimpleStatementHandler，PreparedStatementHandler和CallableStatementHandler，
        //SimpleStatementHandler是用于处理Statement的，PreparedStatementHandler是处理PreparedStatement的，而CallableStatementHandler是
        //处理CallableStatement的。Mybatis在进行Sql语句处理的时候都是建立的RoutingStatementHandler，而在RoutingStatementHandler里面拥有一个
        //StatementHandler类型的delegate属性，RoutingStatementHandler会依据Statement的不同建立对应的BaseStatementHandler，即SimpleStatementHandler、
        //PreparedStatementHandler或CallableStatementHandler，在RoutingStatementHandler里面所有StatementHandler接口方法的实现都是调用的delegate对应的方法。
        //我们在PageInterceptor类上已经用@Signature标记了该Interceptor只拦截StatementHandler接口的prepare方法，又因为Mybatis只有在建立RoutingStatementHandler的时候
        //是通过Interceptor的plugin方法进行包裹的，所以我们这里拦截到的目标对象肯定是RoutingStatementHandler对象。
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        //通过metaObject优雅的访问对象属性，这里访问的是statementHandler的属性
        MetaObject metaObject = MetaObject.forObject(statementHandler,
                SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                new DefaultReflectorFactory());
        //先拦截到 RoutingStatementHandler，里面有个StatementHandler类型的delegate变量，其实现类是PreparedStatementHandler
        //然后获取到 BaseStatementHandler 的成员变量，mappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        //配置文件当中的sql语句的id
        String id = mappedStatement.getId();
        //这里需要匹配要查询的sql语句,也就是dao的接口名称
        if (id.matches(".+ByPage$")) {
            BoundSql boundSql = statementHandler.getBoundSql();
            //原始的sql语句
            String sql = boundSql.getSql();
            String pageSql = sql + " " + "limit " + " " + startIndex + "," + pageSize;
            metaObject.setValue("delegate.boundSql.sql", pageSql);
        }
        //放行方法
        return invocation.proceed();
    }


    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    //在初始化xml配置文件的时候就将plugin插件实例化，并将属性设置好
    public void setProperties(Properties properties) {
        this.startIndex = (String) properties.get("startIndex");
        this.pageSize = (String) properties.get("pageSize");
    }
}
