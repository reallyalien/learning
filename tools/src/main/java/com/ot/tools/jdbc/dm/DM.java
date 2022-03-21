//package com.ot.tools.jdbc.dm;
//
//import dm.jdbc.driver.DmDriver;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.*;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//
///**
// * 达梦数据库测试
// */
//public class DM {
//
//    // 定义 DM JDBC 驱动串
//    String jdbcString = "dm.jdbc.driver.DmDriver";
//    // 定义 DM URL 连接串
//    String urlString = "jdbc:dm://192.168.100.191:5236";
//    // 定义连接用户名
//    String userName = "SYSDBA";
//    // 定义连接用户口令
//    String password = "SYSDBA";//SYSDBA
//    // 定义连接对象
//    Connection conn = null;
//
//
//    @Before
//    public void loadJdbcDriver() throws SQLException {
//        try {
//            System.out.println("Loading JDBC Driver...");
//            // 加载 JDBC 驱动程序
//            Class.forName(jdbcString);
//        } catch (ClassNotFoundException e) {
//            throw new SQLException("Load JDBC Driver Error : " + e.getMessage());
//        } catch (Exception ex) {
//            throw new SQLException("Load JDBC Driver Error : "
//                    + ex.getMessage());
//        }
//    }
//
//    /* 连接 DM 数据库
//     * @throws SQLException 异常 */
//    @Before
//    public void connect() throws SQLException {
//        try {
//            System.out.println("Connecting to DM Server...");
//            // 连接 DM 数据库
//            conn = DriverManager.getConnection(urlString, userName, password);
//        } catch (SQLException e) {
//            throw new SQLException("Connect to DM Server Error : "
//                    + e.getMessage());
//        }
//    }
//
//    /* 关闭连接
//     * @throws SQLException 异常 */
//    public void disConnect() throws SQLException {
//        try {
//            // 关闭连接
//            conn.close();
//        } catch (SQLException e) {
//            throw new SQLException("close connection error : " + e.getMessage());
//        }
//    }
//
//    /* 查询产品信息表
//     * @throws SQLException 异常 */
//    public void queryProduct() throws SQLException {
//        // 查询语句
//        String sql="INSERT INTO DM_MYSQL VALUES (?,?,?,?,?)";
//        for (int i = 1; i <= 100; i++) {
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1,i);
//            ps.setInt(2,i);
//            ps.setString(3,i+"name");
//            ps.setString(4,"cat");
//            ps.setString(5, LocalDateTime.now().toString());
//            ps.execute();
//        }
////        String sql = "select id,name,source from SYSDBA.dept";
////        // 创建语句对象
////        Statement stmt = conn.prepareStatement(sql);
////        // 执行查询
////        ResultSet rs = stmt.executeQuery(sql);
////        // 显示结果集
////        displayResultSet(rs);
////        // 关闭结果集
////        rs.close();
////        // 关闭语句
////        stmt.close();
//
//    }
//    public void findMetaData(){
//        try {
//            String catalog = conn.getCatalog();
//            DatabaseMetaData metaData = conn.getMetaData();
//            ResultSet rs = metaData.getTables(catalog, null, null, null);
//            displayResultSet(rs);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
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
//
//    @Test
//    public void findAll() throws SQLException {
//     this.connect();
//    }
//    @Test
//    public void tt11(){
//        findMetaData();
//    }
//}