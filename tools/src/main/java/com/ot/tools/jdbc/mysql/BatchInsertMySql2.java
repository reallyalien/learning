package com.ot.tools.jdbc.mysql;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * https://blog.csdn.net/seven_3306/article/details/9303879
 * 大批量数据读取导致OOM的问题
 */
public class BatchInsertMySql2 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        final String url = "jdbc:mysql://192.168.197.110:3306/chunjun";
        final String name = "com.mysql.jdbc.Driver";
        final String user = "root";
        final String password = "123456";
        Connection conn = null;
        Class.forName(name);//注册驱动，获得连接
        conn = DriverManager.getConnection(url, user, password);//获取连接
        if (conn != null) {
            System.out.println("获取连接成功");
            BatchInsertMySql2.insert(conn);
        } else {
            System.out.println("获取连接失败");
        }
    }

    public static void insert(Connection conn) {
        // 开始时间
        Long begin = new Date().getTime();
        // sql前缀
        String sql = "INSERT INTO test1 (id,name) VALUES (?,?)";
        int num = 100_0000;
        List<String> list = new ArrayList<>(num);
        for (int i = 1; i <= num; i++) {
            list.add(i + "name");
        }
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (int i = 0; i < num; i++) {
                ps.setObject(1, i + 1);
                ps.setObject(2, list.get(i));
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("插入完成");
    }

    public static int count(Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from test1");
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            return count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
