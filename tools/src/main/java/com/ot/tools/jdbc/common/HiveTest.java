package com.ot.tools.jdbc.common;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HiveTest {

    public static final String url = "jdbc:hive2://192.168.2.130:10000";
    public static final String database = "default";
    public static final String driverName = "org.apache.hive.jdbc.HiveDriver";
    public static final String user = "root";
    public static final String password = "";
    public static Connection connection = null;

    private Long hiveMaxFileSize = 1073741824L;
//    private Long hiveMaxFileSize = 1073741824;

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
        List<String> tablePrimaryKeys = CommonDb.getSchemas(connection, database);
        for (String tablePrimaryKey : tablePrimaryKeys) {
            System.out.println(tablePrimaryKey);
        }
    }

}
