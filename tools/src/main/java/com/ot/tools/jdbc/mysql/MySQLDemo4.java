package com.ot.tools.jdbc.mysql;

import com.ot.tools.jdbc.common.ColumnDescription;
import com.ot.tools.jdbc.common.CommonDb;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class MySQLDemo4 {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        final String url = "jdbc:mysql://192.168.197.110:3306/chunjun";
        final String name = "com.mysql.jdbc.Driver";
        final String user = "root";
        final String password = "123456";
        Class.forName(name);//注册驱动，获得连接

        Properties properties = new Properties();
        properties.setProperty("user", url);
        properties.setProperty("password", password);
        Connection conn = DriverManager.getConnection(url, user, password);//获取连接

        System.out.println("------------------------------------------------");
        String sql = "select * from INFORMATION_SCHEMA.TABLES";
        List<String> list = new ArrayList<>();
        ResultSet schemas = conn.getMetaData().getSchemas();
        while (schemas.next()) {
            list.add(schemas.getString("TABLE_SCHEM"));
        }
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("===========================================================================");
        while (resultSet.next()) {
            String table_name = resultSet.getString("TABLE_NAME");
            String table_catalog = resultSet.getString("TABLE_CATALOG");
            String TABLE_SCHEMA = resultSet.getString("TABLE_SCHEMA");
            System.out.println(table_catalog + ":" + TABLE_SCHEMA + ":" + table_name);
        }


//        System.out.println("---------------------------------xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//        List<String> allTables = CommonDb.getAllTables(conn,null,"ambari_ds_311", Collections.emptyList());
//        System.out.println(allTables);


        System.out.println("========================================================================================");
        List<ColumnDescription> chunjun_dirty_data = CommonDb.getTableColumnMeta(conn, "chunjun.chunjun_dirty_data", true);
        for (ColumnDescription chunjun_dirty_datum : chunjun_dirty_data) {
//            System.out.println(chunjun_dirty_datum.getFieldTypeName()+"("+chunjun_dirty_datum.getDisplaySize()+")");
            System.out.println(chunjun_dirty_datum);
        }

    }
}
