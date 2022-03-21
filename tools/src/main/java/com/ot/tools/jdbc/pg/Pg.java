package com.ot.tools.jdbc.pg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Pg {

    private static final String url="jdbc:postgresql://192.168.1.100:5432";
    private static final String driver="org.postgresql.Driver";


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, "sys", "sys");
        System.out.println(connection);
    }

}
