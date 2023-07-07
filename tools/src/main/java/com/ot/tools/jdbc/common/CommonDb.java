package com.ot.tools.jdbc.common;

import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommonDb {

    /**
     * 获取指定条件下所有的表
     *
     * @param connection
     * @param catalog
     * @param schema
     * @param excludeSchemas
     * @return shcema.tableName
     */
    public static List<String> getAllTables(Connection connection, String catalog, String schema, List<String> excludeSchemas, Boolean isClose) {
        List<String> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = connection.getMetaData().getTables(catalog, schema, "%", new String[]{"TABLE", "VIEW"});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String tableSchem = rs.getString("TABLE_SCHEM");
                if (StringUtils.isNotEmpty(tableName) && excludeSchemas.contains(tableSchem)) {
                    continue;
                }
                if (StringUtils.isNotEmpty(tableSchem)) {
                    list.add(tableSchem + "." + tableName);
                } else if (StringUtils.isNotEmpty(schema)) {
                    list.add(schema + "." + tableName);
                } else {
                    list.add(tableName);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (null == isClose || isClose) {
                try {
                    if (null != connection) {
                        connection.close();
                    }
                    if (null != rs) {
                        rs.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public static List<ColumnDescription> getTableColumnMeta(Connection connection, String tableName, Boolean isClose) {
        List<ColumnDescription> ret = new ArrayList<ColumnDescription>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from " + tableName;
        ResultSet set = null;
        try {
            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();
            ResultSetMetaData m = rs.getMetaData();
            int columns = m.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                String name = m.getColumnLabel(i);
                if (null == name) {
                    name = m.getColumnName(i);
                }
                ColumnDescription cd = new ColumnDescription();
                cd.setFieldName(name);
                cd.setLabelName(name);
                cd.setFieldType(m.getColumnType(i));
                if (0 != cd.getFieldType()) {
                    cd.setFieldTypeName(m.getColumnTypeName(i));
                    cd.setFiledTypeClassName(m.getColumnClassName(i));
                    cd.setDisplaySize(m.getColumnDisplaySize(i));
                    cd.setPrecisionSize(m.getPrecision(i));
                    cd.setScaleSize(m.getScale(i));
                    cd.setAutoIncrement(m.isAutoIncrement(i));
                    cd.setNullable(m.isNullable(i) != ResultSetMetaData.columnNoNulls);
                } else {
                    // 处理视图中NULL as fieldName的情况
                    cd.setFieldTypeName("CHAR");
                    cd.setFiledTypeClassName(String.class.getName());
                    cd.setDisplaySize(1);
                    cd.setPrecisionSize(1);
                    cd.setScaleSize(0);
                    cd.setAutoIncrement(false);
                    cd.setNullable(true);
                }

                boolean signed = false;
                try {
                    signed = m.isSigned(i);
                } catch (Exception ignored) {
                    // This JDBC Driver doesn't support the isSigned method
                    // nothing more we can do here by catch the exception.
                }
                cd.setSigned(signed);
//                cd.setDbType(dbtype);

                ret.add(cd);
            }
            String s1 = tableName.split("\\.")[0];
            String s2 = tableName.split("\\.")[1];
            set = connection.getMetaData().getColumns(null, s1, s2, null);
            while (set.next()) {
                String columnName = set.getString("COLUMN_NAME");
                String remarks = set.getString("REMARKS");
                for (ColumnDescription cd : ret) {
                    if (columnName.equalsIgnoreCase(cd.getFieldName())) {
                        cd.setRemarks(remarks);
                    }
                }
            }
            return ret;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (null == isClose || isClose) {
                try {
                    if (null != connection) {
                        connection.close();
                    }
                    if (null != rs) {
                        rs.close();
                    }
                    if (null != set) {
                        set.close();
                    }
                    if (null != pstmt) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<String> getTablePrimaryKeys(Connection connection, String catalog, String schema, String table) {
        List<String> pks = new ArrayList<>();
        try {
            ResultSet primaryKeys = connection.getMetaData().getPrimaryKeys(catalog, schema, table);
            while (primaryKeys.next()) {
                String name = primaryKeys.getString("COLUMN_NAME");
                pks.add(name);
            }
            return pks;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static List<String> getSchemas(Connection connection, String catalog) {
        List<String> pks = new ArrayList<>();
        try {
            ResultSet schemas = connection.getMetaData().getSchemas(catalog, null);
            while (schemas.next()) {
                String name = schemas.getString("TABLE_SCHEM");
                pks.add(name);
            }
            return pks;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
