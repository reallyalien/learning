package com.ot.springdbutils.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 如果当前线程上有connection，直接获取，否则创建一个connection，并与当前线程绑定
 */
@Component
public class ConnectionUtil {

    /**
     * 存放connection的容器
     */
    private static ThreadLocal<Connection> map = new ThreadLocal<>();

    @Autowired
    private DataSource dataSource;

    private Object lock = new Object();

    public Connection getConnection() {
        Connection conn = map.get();
        synchronized (lock) {
            if (conn == null) {
                try {
                    conn = dataSource.getConnection();
                    map.set(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return conn;
    }

    public void remove(){
        map.remove();
    }

}
