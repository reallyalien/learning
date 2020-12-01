package com.ot.tools.jdbc.kbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class KbaseDemo {
    public static final String url = "jdbc:kingbase8://192.168.106.160:54321/TEST";
    public static final String driver_name = "com.kingbase8.Driver";
    public static final String user = "SYSTEM";
    public static final String password = "Admin123";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName(driver_name);
        Connection conn = DriverManager.getConnection(url, user, password);

        String sql="INSERT INTO PUBLIC.NEWTABLE  VALUES (?,?,?,?,?)";
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
