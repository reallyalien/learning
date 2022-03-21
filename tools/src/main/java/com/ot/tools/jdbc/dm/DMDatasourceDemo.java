//package com.ot.tools.jdbc.dm;
//
//import dm.jdbc.driver.DmdbDataSource;
//import dm.jdbc.driver.DmdbDataSourceFactory;
//import org.junit.After;
//import org.junit.Test;
//
//import javax.sql.DataSource;
//import java.sql.*;
//
//public class DMDatasourceDemo {
//
//    // 定义 DM JDBC 驱动串
//    private static String jdbcDriverString = "dm.jdbc.driver.DmDriver";
//    // 定义 DM URL 连接串
//    private static String urlString = "jdbc:dm://127.0.0.1:5236";
//    // 定义连接用户名
//    private static String userName = "SYSDBA";
//    // 定义连接用户口令
//    private static String password = "root123456";//SYSDBA
//    // 定义数据库连接池
//    private static DmdbDataSource dataSource;
//
//    static {
//        dataSource = new DmdbDataSource();
//        dataSource.setUser(userName);
//        dataSource.setPassword(password);
//        dataSource.setPort(5263);
//        dataSource.setURL(urlString);
//    }
//
//    public Connection getConnection() {
//        return dataSource.getConnection();
//    }
//
//    @Test
//   public void read() throws SQLException {
//        Connection connection = getConnection();
//        String sql = "SELECT * FROM DMHR.JOB limit 0,10";
//        PreparedStatement ps  = connection.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//        displayResultSet(rs);
//        connection.close();
//    }
//    /* 显示结果集
//     * @param rs 结果集对象
//     * @throws SQLException 异常 */
//    private void displayResultSet(ResultSet rs) throws SQLException {
//        // 取得结果集元数据
//        ResultSetMetaData rsmd = rs.getMetaData();
//        // 取得结果集所包含的列数
//        int numCols = rsmd.getColumnCount();
//        // 显示列标头
//        for (int i = 1; i <= numCols; i++) {
//            if (i > 1) {
//                System.out.print(",");
//            }
//            System.out.print(rsmd.getColumnLabel(i));
//        }
//        System.out.println("");
//        // 显示结果集中所有数据
//        while (rs.next()) {
//            for (int i = 1; i <= numCols; i++) {
//                if (i > 1) {
//                    System.out.print(",");
//                }
//                System.out.print(rs.getString(i));
//            }
//            System.out.println("");
//        }
//    }
//}
