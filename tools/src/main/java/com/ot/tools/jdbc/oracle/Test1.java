package com.ot.tools.jdbc.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Test1 {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        final String url = "jdbc:oracle:thin:@192.168.100.188:1521:orcl";
        final String name = "oracle.jdbc.driver.OracleDriver";
        final String user = "shareuser";
        final String password = "shareuser";
        Class.forName(name);//注册驱动，获得连接
        try {
            Connection conn = DriverManager.getConnection(url, user, password);//获取连接
            String sql = " merge into SHAREUSER.STUDENT7 " +
                    " using  ( select  ? ID, ? NAME, ? AGE from dual ) t " +
                    " on (SHAREUSER.STUDENT7.ID =  t.ID) " +
                    " when matched then  " +
                    " UPDATE SET SHAREUSER.STUDENT7.NAME = t.NAME,SHAREUSER.STUDENT7.AGE = t.AGE " +
                    " when not matched then  " +
                    " INSERT VALUES (?,?,? )";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, 3);
            ps.setObject(2, "CC");
            ps.setObject(3, 3);
            ps.execute();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }


    }

}
