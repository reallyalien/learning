package com.ot.tools.jdbc.shentong;

import com.oscar.core.BaseConnection;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;


/**
 * 神通数据库
 * mvn install:install-file -Dfile=oscarJDBC16.jar  -DgroupId=com.oscar -DartifactId=oscarJDBC -Dversion=1.6 -Dpackaging=jar -DgeneratePom=true
 *
 *
 * <dependency>
 * <groupId>com.oscar</groupId>
 * <artifactId>oscarJDBC</artifactId>
 * <version>1.6</version>
 * </dependency>
 */
public class ST {

    public static final String url = "jdbc:oscar://192.168.100.191:2003/osrdb";
    public static final String driver_name = "com.oscar.Driver";
    public static final String user = "sysdba";
    public static final String password = "szoscar55";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName(driver_name);
        Connection conn = DriverManager.getConnection(url, user, password);
//        DatabaseMetaData metaData = conn.getMetaData();
//        ResultSet rs = metaData.getTables(conn.getCatalog(), null, null, null);
//        displayResultSet(rs);
//        ResultSet rs1 = metaData.getPrimaryKeys(conn.getCatalog(), "sysdba", "employee");
//        displayResultSet(rs1);
//        BaseConnection baseConnection = (BaseConnection) conn;
        String sql1 = "insert into SYSDBA.TEST values (?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql1);
        for (int i = 100; i < 200; i++) {
            ps.setInt(1, i);
            ps.setInt(2, i);
            ps.setString(3, i + "name");
            ps.setString(4, "怪物");
            ps.setString(5, LocalDateTime.now().toString());
            ps.execute();
        }
    }

    private static void displayResultSet(ResultSet rs) throws SQLException {
        // 取得结果集元数据
        ResultSetMetaData rsmd = rs.getMetaData();
        // 取得结果集所包含的列数
        int numCols = rsmd.getColumnCount();
        // 显示列标头
        for (int i = 1; i <= numCols; i++) {
            if (i > 1) {
                System.out.print(",");
            }
            System.out.print(rsmd.getColumnLabel(i));
        }
        System.out.println("");
        // 显示结果集中所有数据
        while (rs.next()) {
            for (int i = 1; i <= numCols; i++) {
                if (i > 1) {
                    System.out.print(",");
                }
                System.out.print(rs.getString(i));
            }
            System.out.println("");
        }
    }

    @Test
    public void a(){

    }
}
