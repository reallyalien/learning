package com.ot.tools.jdbc.shentong;

import java.sql.*;

public class TestST {

    public static void main(String args[]) {

        String url = "jdbc:oscar://192.168.100.191:2003";
        Connection con = null;
        Statement stmt;
        PreparedStatement pstmt;
        String insertSql = "INSERT INTO EMPLOYEE VALUES(?,?)";
        String updateSql = "UPDATE EMPLOYEE SET NAME = ? WHERE ID = ?";
        String querySql = "SELECT ID, NAME FROM EMPLOYEE";
        String deleteSql = "DELETE FROM EMPLOYEE";


        try {
            Class.forName("com.oscar.Driver");
        }
        catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        try {
            con = DriverManager.getConnection(url,"SYSDBA", "szoscar55");
            stmt = con.createStatement();
            stmt.execute("CREATE TABLE EMPLOYEE(ID INT,NAME VARCHAR(20))");

//            con.setAutoCommit(false);
//            pstmt = con.prepareStatement(insertSql);
//            for (int i = 1; i <= 10; i++) {
//                pstmt.setInt(1,i);
//                pstmt.setString(2,"name["+i+"]");
//                pstmt.execute();
//            }
//            con.commit();
//
//            pstmt = con.prepareStatement(updateSql);
//            int[] ids = {1,3,5,9};
//            for (int i = 0; i < ids.length; i++) {
//                pstmt.setString(1, "name["+(ids[i]+10)+"]");
//                pstmt.setInt(2, ids[i]);
//                pstmt.executeUpdate();
//                con.commit();
//            }
//            con.setAutoCommit(true);
//
//            pstmt.close();
//
//            ResultSet rs = stmt.executeQuery(querySql);
//            while (rs.next()) {
//                String name = rs.getString("NAME");
//                int id = rs.getInt("ID");
//                System.out.println("id:"+ id + "  name:" + name);
//            }
//
//
//            stmt.executeUpdate(deleteSql);
//
//
//            stmt.executeUpdate("DROP TABLE EMPLOYEE");


            stmt.close();
            con.close();
        }
        catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            if (con != null) {
                try {
                    System.err.print("Transaction is being ");
                    System.err.println("rolled back");
                    con.rollback();
                }
                catch (SQLException excep) {
                    System.err.print("SQLException: ");
                    System.err.println(excep.getMessage());
                }
            }
        }
    }
}