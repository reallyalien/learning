package com.ot.tools.jdbc.starrocks;

import java.sql.*;

public class StartRockTest {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        final String url = "jdbc:mysql://192.168.2.122:9030";
        final String url = "jdbc:mysql://192.168.2.122:9030?allowLoadLocalInfile=false&autoDeserialize=false&allowLocalInfile=false&allowUrlInLocalInfile=false";
        final String name = "com.mysql.cj.jdbc.Driver";
        final String user = "root";
        final String password = "root";
        Class.forName(name);

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
//            String sql = "select * from iceberg_catalog_122.iceberg_test.audit_project_160w";
//            String sql = "SELECT count(a.RowGuid) from iceberg_catalog_122.iceberg_test.audit_project_160w a WHERE\n" +
//                    "        a.status in ('26','28','30','40','50','90','97','98','99','110')\n" +
//                    "        and\n" +
//                    "        a.Operatedate > '2003-01-01 00:00:01'  and a.Operatedate < '2033-01-01 00:00:01'\n" +
//                    "        and a.TASKTYPE = '2'\n" +
//                    "        and a.FLOWSN !='null';";
//            String sql="SELECT  a.TASK_ID task_id, count(1) bj_num\n" +
//                    "from iceberg_catalog_122.iceberg_test.audit_project_160w a,\n" +
//                    "     iceberg_catalog_122.iceberg_test.audit_task b\n" +
//                    "where a.TASK_ID = b.TASK_ID\n" +
//                    "  and a.AREACODE = '140400'\n" +
//                    "  and b.IS_ENABLE = 1\n" +
//                    "  and b.IS_EDITAFTERIMPORT = 1\n" +
//                    "  and (b.IS_HISTORY is null or b.IS_HISTORY = 0)\n" +
//                    "group by a.TASK_ID;";
            String sql="SET CATALOG iceberg_catalog_122;select * from iceberg_test.audit_project_160w limit 10;";
//            String sql="\n" +
//                    "CREATE\n" +
//                    "MATERIALIZED VIEW count_audit_project_160w\n" +
//                    "REFRESH ASYNC  EVERY (interval 1 day)\n" +
//                    "    AS\n" +
//                    "SELECT count(1)\n" +
//                    "from iceberg_catalog_122.iceberg_test.audit_project_160w;\n";
            PreparedStatement ps = conn.prepareStatement(sql);
            long s = System.currentTimeMillis();
//            boolean execute = ps.execute();
            ResultSet rs = ps.executeQuery();
//            while (rs.next()){
//                String string = rs.getString("Catalog");
//                System.out.println(string);
//            }
//            displayResultSet(rs);
            long e = System.currentTimeMillis();
            System.out.println("耗时" + (e - s) +"毫秒");
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }


    }

    private static void displayResultSet(ResultSet rs) throws SQLException, InterruptedException {
        // 取得结果集元数据
        ResultSetMetaData rsmd = rs.getMetaData();
        // 取得结果集所包含的列数
        int numCols = rsmd.getColumnCount();
//        Thread.sleep(5000);
        System.out.println("============================================================================================================");
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
        System.out.println("============================================================================================================");
    }

}
