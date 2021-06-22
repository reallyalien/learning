package com.ot.tools.jdbc.hive;

import com.google.inject.internal.util.$AsynchronousComputationException;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.hive.jdbc.HiveQueryResultSet;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;

public class Hive {


    private static final String DRIVER = "org.apache.hive.jdbc.HiveDriver";

    private static final String URL = "jdbc:hive2://192.168.100.190:10000";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "liuyonghua";

    public static DataSource dataSource() {
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
        String sql = "desc formatted test1.sequence_table";
        ResultSet rs = null;
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
        System.out.println("啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦 ");
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

    public static String local() throws SQLException {
        Connection connection = getConnection();
        String tableName = "a_table_like";
        PreparedStatement ps = connection.prepareStatement("show create table " + tableName);
        ResultSet rs = ps.executeQuery();
        HiveQueryResultSet resultSet = (HiveQueryResultSet) rs;
        StringBuilder sb = new StringBuilder();
        while (resultSet.next()) {
            String string = resultSet.getString(1);
            sb.append(string).append(" ");
        }
        String str = (String) sb.subSequence(0, sb.length() - 1);
        String location = "LOCATION";
        String tblproperties = "TBLPROPERTIES";
        String s = str.substring(str.indexOf(location) + location.length(), str.indexOf(tblproperties));
        s=s.substring(s.indexOf("\'")+1,s.lastIndexOf("\'"));
        s=s.substring(7);
        s = s.substring(s.indexOf("/"));
       return s;
    }
    public static void main(String[] args) throws SQLException, InterruptedException {
        Connection connection = getConnection();
        String tableName = "test1.student_bck_json";
//        PreparedStatement ps = connection.prepareStatement("desc formatted " + tableName);
//        ResultSet rs = ps.executeQuery();
//        HiveQueryResultSet resultSet = (HiveQueryResultSet) rs;
//        displayResultSet(resultSet);
    }
}
