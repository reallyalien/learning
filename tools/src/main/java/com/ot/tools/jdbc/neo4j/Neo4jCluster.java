package com.ot.tools.jdbc.neo4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Neo4jCluster {

    /**
     * 如果连接上的是flower，则会自动路由到leader节点执行写操作
     * @param args
     */
    public static void main(String[] args) {
        String url = "jdbc:neo4j:neo4j:127.0.0.1:7688,127.0.0.1:7687,127.0.0.1:7689";

        String driverName = "org.neo4j.jdbc.Driver";
        String username = "neo4j";
        String password = "root";
        Connection connection = null;
        PreparedStatement stmt = null;       //采用预编译，和关系数据库不一样的是,参数需要使用{1},{2},而不是?
        ResultSet rs = null;
        try {
            DriverManager.registerDriver(new org.neo4j.jdbc.boltrouting.BoltRoutingNeo4jDriver());
            connection = DriverManager.getConnection(url, username, password);
            String sql = "CREATE (a:klkl {name: 'aaaaalll'})";
            stmt = connection.prepareStatement(sql);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
