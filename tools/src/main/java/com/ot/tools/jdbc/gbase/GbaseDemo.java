package com.ot.tools.jdbc.gbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GbaseDemo {

    private static final String DB_DRIVER = "com.gbase.jdbc.Driver";

    private static final String DB_CONNECTION = "jdbc:gbase://192.168.100.191:5258/test";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Admin123";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);
        Connection conn = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        String sql="INSERT INTO test.gbase_mysql VALUES (?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 101; i <= 200; i++) {
            ps.setInt(1,i);
            ps.setInt(2,i);
            ps.setString(3,i+"name");
            ps.setString(4,"cat");
            ps.setString(5, LocalDateTime.now().toString());
            ps.execute();
        }
    }
}
