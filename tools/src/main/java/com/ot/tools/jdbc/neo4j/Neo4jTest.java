package com.ot.tools.jdbc.neo4j;

import java.sql.*;

public class Neo4jTest {
    public static void main(String[] args) {
        //协议端口号不同,bolt协议7687，http协议7474，
        String url = "jdbc:neo4j:bolt://192.168.100.189:7687/";
//        String url = "jdbc:neo4j:http://192.168.100.189:7474/";

        String driverName = "org.neo4j.jdbc.Driver";
        String username = "neo4j";
        String password = "Admin123";
        Connection connection = null;
        PreparedStatement stmt = null;       //采用预编译，和关系数据库不一样的是,参数需要使用{1},{2},而不是?
        ResultSet rs = null;
        try {
            DriverManager.registerDriver(new org.neo4j.jdbc.Driver());
            connection = DriverManager.getConnection(url, username, password);
//            DatabaseMetaData metaData = connection.getMetaData();
//            rs = metaData.getTables(connection.getCatalog(), null, null, null);
            stmt = connection.prepareStatement("match (p) return distinct labels (p)");
            rs = stmt.executeQuery();
            displayResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void displayResultSet(ResultSet rs) throws SQLException {
        // 取得结果集元数据
        ResultSetMetaData rsmd = rs.getMetaData();
        // 取得结果集所包含的列数
        int numCols = rsmd.getColumnCount();
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
    }
}
