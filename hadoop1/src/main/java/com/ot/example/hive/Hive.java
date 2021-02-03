package com.ot.example.hive;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class Hive {


    private static final String DRIVER = "org.apache.hive.jdbc.HiveDriver";

    private static final String URL = "jdbc:hive2://192.168.100.190:10000/test1";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "liuyonghua";

    public static DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            boolean valid = connection.isValid(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static ResultSet query() throws SQLException {
//        Connection connection = dataSource().getConnection();
        Connection connection = getConnection();
        String sql="desc formatted test1.sequence_table";
        ResultSet rs=null;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    private static void displayResultSet(ResultSet rs) throws SQLException, InterruptedException {
        // 取得结果集元数据
        ResultSetMetaData rsmd = rs.getMetaData();
        // 取得结果集所包含的列数
        int numCols = rsmd.getColumnCount();
//        Thread.sleep(5000);
        System.out.println("============================================================================================================");
        // 显示列标头
        for (int i = 1; i <= numCols; i++) {
            if (i > 1) {
                System.out.print(",");
            }
            System.out.print(rsmd.getColumnLabel(i));
        }
        System.out.println("");
        // 显示结果集中所有数据
        while (rs.next()) {
            for (int i = 1; i <= numCols; i++) {
                if (i > 1) {
                    System.out.print(",");
                }
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
        System.out.println("============================================================================================================");
    }

    public static void main(String[] args) throws SQLException, InterruptedException {
        ResultSet query = query();
        displayResultSet(query);
//        System.out.println(query);
    }
}
