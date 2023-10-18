package com.ot.tools.jdbc.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MysqlTest {

    public static final String url = "jdbc:mysql://192.168.197.110:3306";
    public static final String database = "chunjun";
    public static final String driverName = "com.mysql.jdbc.Driver";
    public static final String user = "root";
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
//    public static String getCreateTableSql(String tableName, List<ColumnDTO> columnList) {
//        StringBuilder sb = new StringBuilder(Const.CREATE_TABLE)
//                .append(Const.IF_NOT_EXISTS).append(StringUtils.isBlank(tableName) ? "__" : tableName).append("(\n");
//        for (ColumnDTO columnDTO : columnList) {
//            sb.append(columnDTO.getName()).append(" ").append(columnDTO.getType()).append(" ");
//            if (StringUtils.isNotEmpty(columnDTO.getComment())) {
//                sb.append("comment '").append(columnDTO.getComment()).append("'").append("',");
//            } else {
//                sb.append(",\n");
//            }
//        }
//        String str = sb.substring(0, sb.length() - 2) + ");";
//        return str;
//    }
}
