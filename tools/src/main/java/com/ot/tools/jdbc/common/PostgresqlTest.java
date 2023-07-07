package com.ot.tools.jdbc.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class PostgresqlTest {

    public static final String url = "jdbc:postgresql://192.168.197.110:5432";
    public static final String database = "chunjun_test";
    public static final String driverName = "org.postgresql.Drive";
    public static final String user = "postgres";
    public static final String password = "123456";
    public static Connection connection = null;

    static {
        try {
            connection = DriverManager.getConnection(url + "/" + database, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 测试获取主键
     */
    public static void testQueryTablePrimaryKeysTest() {
        List<String> tablePrimaryKeys = CommonDb.getTablePrimaryKeys(connection, null, database, "test_sink");
        for (String tablePrimaryKey : tablePrimaryKeys) {
            System.out.println(tablePrimaryKey);
        }
    }

    /**
     * 测试获取schema
     */
    public static void testGetSchemas() {
        List<String> tablePrimaryKeys = CommonDb.getSchemas(connection, null);
        for (String tablePrimaryKey : tablePrimaryKeys) {
            System.out.println(tablePrimaryKey);
        }
    }

}
