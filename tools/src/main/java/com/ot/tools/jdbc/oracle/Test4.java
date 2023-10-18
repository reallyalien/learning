package com.ot.tools.jdbc.oracle;

import java.sql.*;

public class Test4 {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        final String url = "jdbc:oracle:thin:@//127.0.0.1:4444/helowin";
        final String name = "oracle.jdbc.driver.OracleDriver";
        final String user = "system";
        final String password = "system";
        Class.forName(name);//注册驱动，获得连接
        try {
            Connection conn = DriverManager.getConnection(url, user, password);//获取连接
            String sql="select * from PM.ONLINE_MEDIA";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            System.out.println(resultSet);
            System.out.println(conn);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

}
