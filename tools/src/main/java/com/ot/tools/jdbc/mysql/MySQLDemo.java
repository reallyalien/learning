package com.ot.tools.jdbc.mysql;

import java.sql.*;

public class MySQLDemo {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        final String url = "jdbc:mysql://127.0.0.1:3306";
        final String name = "com.mysql.jdbc.Driver";
        final String user = "root";
        final String password = "root";
        Class.forName(name);//注册驱动，获得连接
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    Connection conn = DriverManager.getConnection(url, user, password);//获取连接
                    //必须设置这个，否则mysql执行完之后会自动提交，锁立马被释放
                    // show VARIABLES like '%innodb_lock_wait_timeout%'; 默认其他线程阻塞50s,
                    conn.setAutoCommit(false);
                    System.out.println("进来了");
                    //相当于悲观锁，当前线程不释放锁，其他线程无法获取
                    PreparedStatement ps = conn.prepareStatement("select * from xxl_job_lock where lock_name = 'schedule_lock' for update");
                    ps.execute();
//                    conn.commit();
                    System.out.println("执行了");
                } catch (Exception throwables) {
                    throwables.printStackTrace();
                }

            }).start();

        }
    }

}
