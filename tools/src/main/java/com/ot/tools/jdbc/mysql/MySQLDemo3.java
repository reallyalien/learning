package com.ot.tools.jdbc.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLDemo3 {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        final String url = "jdbc:mysql://127.0.0.1:3306/seata_account";
        final String name = "com.mysql.jdbc.Driver";
        final String user = "root";
        final String password = "root";
        Class.forName(name);//注册驱动，获得连接

        Connection conn = DriverManager.getConnection(url, user, password);//获取连接
        //必须设置这个，否则mysql执行完之后会自动提交，锁立马被释放
        // show VARIABLES like '%innodb_lock_wait_timeout%'; 默认其他线程阻塞50s,


    }
}
