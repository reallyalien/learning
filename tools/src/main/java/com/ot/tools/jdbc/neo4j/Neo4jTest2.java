package com.ot.tools.jdbc.neo4j;

import org.neo4j.driver.*;
import org.neo4j.driver.Driver;

import java.sql.*;
import java.util.List;

public class Neo4jTest2 {
    public static void main(String[] args) {
        //协议端口号不同,bolt协议7687，http协议7474，
        String url = "bolt://192.168.100.189:7687/";
//        String url = "jdbc:neo4j:http://192.168.100.189:7474/";

        String driverName = "org.neo4j.jdbc.Driver";
        String username = "neo4j";
        String password = "Admin123";
        Connection connection = null;
        PreparedStatement stmt = null;       //采用预编译，和关系数据库不一样的是,参数需要使用{1},{2},而不是?
        ResultSet rs = null;
        try {
            Driver driver = GraphDatabase.driver(url, AuthTokens.basic(username, password));
            Session session = driver.session();
            Result result = session.run("match (p) return distinct labels (p)");
            List<Record> list = result.list();
            for (Record record : list) {
                System.out.println(record);
            }
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
