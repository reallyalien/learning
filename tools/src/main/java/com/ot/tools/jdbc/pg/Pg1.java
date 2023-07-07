package com.ot.tools.jdbc.pg;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Pg1 {

    private static final String url = "jdbc:postgresql://192.168.197.110:5432/chunjun_test";
    private static final String driver = "org.postgresql.Driver";


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, "postgres", "123456");
        System.out.println(connection);
        List<String> list = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet schemas = metaData.getSchemas();
        while (schemas.next()) {
            String tableSchema = schemas.getString("TABLE_SCHEM");
            list.add(tableSchema);
        }
//        ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE", "VIEW"});
//        while (tables.next()) {
//            String tableName = tables.getString("TABLE_NAME");
//            System.out.println(tableName);
//        }
//        System.out.println();


//        String sql = "select * from test1.user_sink";
//        PreparedStatement ps = connection.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//        ResultSetMetaData metaData1 = rs.getMetaData();
//        for (int i = 1; i <= 2; i++) {
//            int columnType = metaData1.getColumnType(i);
//            String columnTypeName = metaData1.getColumnTypeName(1);
//            String columnName = metaData1.getColumnName(i);
//            int columnDisplaySize = metaData1.getColumnDisplaySize(i);
//            System.out.println();


//        }


//        System.out.println("===================================");
//        List<String> allTables = CommonDb.getAllTables(connection, "chunjun_test", null, Collections.emptyList());
//        for (String allTable : allTables) {
//            System.out.println(allTable);
//        }

//        List<ColumnDescription> chunjun_dirty_data = CommonDb.getTableColumnMeta(connection, "test.user_sink");
//        for (ColumnDescription chunjun_dirty_datum : chunjun_dirty_data) {
////            System.out.println(chunjun_dirty_datum.getFieldTypeName()+"("+chunjun_dirty_datum.getDisplaySize()+")");
//            System.out.println(chunjun_dirty_datum);
//        }
    }

}
