package com.ot.tools.jdbc.mysql;

import org.apache.ibatis.mapping.ResultSetType;

import java.sql.*;
import java.util.Date;

/**
 * https://blog.csdn.net/seven_3306/article/details/9303879
 * 大批量数据读取导致OOM的问题
 */
public class BatchInsertMySql {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        final String url = "jdbc:mysql://127.0.0.1/clouddb01";
        final String name = "com.mysql.jdbc.Driver";
        final String user = "root";
        final String password = "root";
        Connection conn = null;
        Class.forName(name);//注册驱动，获得连接
        conn = DriverManager.getConnection(url, user, password);//获取连接
        if (conn != null) {
            System.out.println("获取连接成功");
//            BatchInsertMySql.insert(conn);
            int count = count(conn);
            System.out.println(count);
        } else {
            System.out.println("获取连接失败");
        }

    }

    public static void insert(Connection conn) {
        // 开始时间
        Long begin = new Date().getTime();
        // sql前缀
        String prefix = "INSERT INTO test1 (dname,db_source) VALUES ";
        try {
            // 保存sql后缀
            StringBuffer suffix;
            // 设置事务为非自动提交
            conn.setAutoCommit(false);
            // 比起st，pst会更好些
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("", ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);//准备执行语句
            pst.setFetchDirection(ResultSet.FETCH_REVERSE);
            // 外层循环，总提交事务次数
            for (int i = 1; i <= 10; i++) {
                suffix = new StringBuffer();
                // 第j次提交步长
                for (int j = 1; j <= 1000000; j++) {
                    // 构建SQL后缀
                    suffix.append("('"+i * j + "name'" + "," + "'clouddb01'"+"),");
                }
                // 构建完整SQL
                String sql = prefix + suffix.substring(0, suffix.length()-1);
                // 添加执行SQL
                pst.addBatch(sql);
                // 执行操作
                pst.executeBatch();
                // 提交事务
                conn.commit();
                // 清空上一次添加的数据
                suffix = suffix.delete(0,suffix.length());
            }
            // 关闭连接
            pst.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 结束时间
        Long end = new Date().getTime();
        // 耗时
        System.out.println("1000万条数据插入花费时间 : " + (end - begin) / 1000 + " s");
        System.out.println("插入完成");
    }

    public static int count(Connection conn){
        try {
            PreparedStatement ps = conn.prepareStatement("select * from dept");
            ResultSet rs = ps.executeQuery();
            int count=0;
            while (rs.next()){
                count++;
            }
            return count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
