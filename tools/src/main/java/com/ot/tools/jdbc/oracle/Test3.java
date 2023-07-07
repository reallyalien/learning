package com.ot.tools.jdbc.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Test3 {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        final String url = "jdbc:oracle:thin:@//192.168.2.188:1521/ORCL";
        final String name = "oracle.jdbc.driver.OracleDriver";
        final String user = "system";
        final String password = "chengyi123";
        Class.forName(name);//注册驱动，获得连接
        try {
            Connection conn = DriverManager.getConnection(url, user, password);//获取连接
            String sql = "create table aaaddad (\n" +
                    "DEPTNO  INTEGER,\n" +
                    "DNAME  NVARCHAR2(14),\n" +
                    "LOC  NVARCHAR2(13)\n" +
                    " );\n" +
                    "comment on table  aaaddad is '';\n" +
                    "comment on column aaaddad.DEPTNO is 'null';\n" +
                    "comment on column aaaddad.DNAME is 'null';\n" +
                    "comment on column aaaddad.LOC is 'null';\n";
            PreparedStatement
                    ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

}
