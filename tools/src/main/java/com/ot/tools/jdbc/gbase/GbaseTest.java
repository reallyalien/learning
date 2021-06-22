package com.ot.tools.jdbc.gbase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GbaseTest {

    private static final String driver = "com.gbase.jdbc.Driver";
    public static final String url = "jdbc:gbase://192.168.100.191:5258";
    public static final String username = "root";
    public static final String password = "Admin123";

    private static Connection connection;

    public static void connection() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);
    }

    public static List<String> findAllSchema() throws SQLException {
        List<String> list = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet schemas = metaData.getSchemas();
        while (schemas.next()) {
            String tableSchema = schemas.getString("TABLE_SCHEM");
            String tableSchema1 = schemas.getString("TABLE_CATALOG");
            list.add(tableSchema);
        }
        return list;
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connection();
        findAllSchema().stream().forEach(System.out::println);

    }
}
