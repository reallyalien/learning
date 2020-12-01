package com.ot.tools.jdbc.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDemo {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        final String url = "jdbc:mysql://127.0.0.1";
        final String name = "com.mysql.jdbc.Driver";
        final String user = "root";
        final String password = "root";
        Class.forName(name);//注册驱动，获得连接
        Connection conn = DriverManager.getConnection(url, user, password);//获取连接
        if (conn != null) {
            System.out.println("获取连接成功");
            BatchInsertMySql.insert(conn);
        } else {
            System.out.println("获取连接失败");
        }
    }

}
