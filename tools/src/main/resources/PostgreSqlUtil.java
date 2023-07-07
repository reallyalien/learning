package org.apache.dolphinscheduler.data.integration.offline.util;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dolphinscheduler.data.integration.offline.constans.Const;
import org.apache.dolphinscheduler.data.integration.offline.dto.ColumnDTO;
import org.apache.dolphinscheduler.data.integration.offline.model.ColumnMetaData;
import org.apache.dolphinscheduler.spi.datasource.BaseConnectionParam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PostgreSqlUtil {

    private static final List<String> DEFAULT_SCHEMA = Arrays.asList("public", "information_schema", "pg_catalog");
    private static final String QUERY_ALL_TABLES_SQL = "SELECT TABLE_SCHEMA,TABLE_NAME FROM INFORMATION_SCHEMA.TABLES where table_schema not in ('pg_catalog','information_schema')";
    private static final String QUERY_ALL_COLUMNS_SQL = "SELECT n.nspname\n" +
            "     , a.attnum\n" +
            "     , a.attname     AS field\n" +
            "     , t.typname     AS type\n" +
            "     , a.attlen      AS length\n" +
            "     , a.atttypmod   AS lengthvar\n" +
            "     , a.attnotnull  AS notnull\n" +
            "     , b.description AS comment\n" +
            "FROM pg_namespace n,\n" +
            "     pg_class c,\n" +
            "     pg_attribute a\n" +
            "         LEFT JOIN pg_description b\n" +
            "                   ON a.attrelid = b.objoid\n" +
            "                       AND a.attnum = b.objsubid,\n" +
            "     pg_type t\n" +
            "WHERE n.nspname = ?\n" +
            "  AND c.relname = ?\n" +
            "  AND a.attnum > 0\n" +
            "  AND a.attrelid = c.oid\n" +
            "  AND a.atttypid = t.oid\n" +
            "  and n.oid = c.relnamespace\n" +
            "ORDER BY a.attnum;\n";

    /**
     * 创建表
     *
     * @param tableName
     * @param columnList
     * @return
     */
    public static void createTable(BaseConnectionParam connectionParam, String tableName, List<ColumnDTO> columnList) throws SQLException {
        StringBuilder createTableSql = new StringBuilder();
        createTableSql.append(Const.CREATE_TABLE).append(Const.IF_NOT_EXISTS);
        String[] split = tableName.split("\\.");
        StringBuilder createSchemaSql = new StringBuilder();
        if (split.length == 2 && !DEFAULT_SCHEMA.contains(split[0])) {
            createSchemaSql.append(Const.CREATE_SCHEMA).append(Const.IF_NOT_EXISTS).append(split[0]).append(";\n");
        }
        createTableSql.append(tableName).append(" (\n");
        for (ColumnDTO columnDTO : columnList) {
            createTableSql.append(columnDTO.getName()).append(" ").append(columnDTO.getType()).append(" ").append(",").append("\n");
        }
        String str = createTableSql.substring(0, createTableSql.length() - 2);
        createSchemaSql.append(str).append(");\n");
        String format = "comment on column " + tableName + ".%s is '%s';\n";
        for (ColumnDTO columnDTO : columnList) {
            if (StringUtils.isNotEmpty(columnDTO.getComment())) {
                createSchemaSql.append(String.format(format, columnDTO.getName(), columnDTO.getComment()));
            }
        }
        CommonDbUtil.executeTxSqlVoid(connectionParam, createSchemaSql.toString());
    }

    public static List<String> getAllTable(Connection connection, String database) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(QUERY_ALL_TABLES_SQL);
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            String tableSchema = rs.getString("TABLE_SCHEMA");
            String tableName = rs.getString("TABLE_NAME");
            list.add(tableSchema + "." + tableName);
        }
        CommonDbUtil.close(connection, ps, rs);
        return list;
    }

    public static List<Map<String, String>> getTableColumn(Connection connection, String tableName) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(QUERY_ALL_COLUMNS_SQL);
        String[] split = tableName.split("\\.");
        ps.setString(1, split[0]);
        ps.setString(2, split[1]);
        ResultSet rs = ps.executeQuery();
        List<Map<String, String>> list = Lists.newArrayList();
        while (rs.next()) {
            String field = rs.getString("field");
            String type = rs.getString("type");
            String comment = rs.getString("comment");
            Map<String, String> tmp = Maps.newHashMap();
            tmp.put("name", field);
            tmp.put("type", type);
            tmp.put("comment", comment);
            list.add(tmp);
        }
        CommonDbUtil.close(connection, ps, rs);
        return list;
    }

    public static String getFieldDefinition(ColumnMetaData v, List<String> pks, boolean useAutoInc, boolean addCr) {
        String fieldname = v.getName();
        int length = v.getLength();
        int precision = v.getPrecision();
        int type = v.getType();

        String retval = " \"" + fieldname + "\"   ";

        switch (type) {
            case ColumnMetaData.TYPE_TIMESTAMP:
                retval += "TIMESTAMP";
                break;
            case ColumnMetaData.TYPE_TIME:
                retval += "TIME";
                break;
            case ColumnMetaData.TYPE_DATE:
                retval += "DATE";
                break;
            case ColumnMetaData.TYPE_BOOLEAN:
                retval += "VARCHAR(32)";
                break;
            case ColumnMetaData.TYPE_NUMBER:
            case ColumnMetaData.TYPE_INTEGER:
            case ColumnMetaData.TYPE_BIGNUMBER:
                if (!CollectionUtils.isEmpty(pks) && pks.contains(fieldname)) {
                    if (useAutoInc) {
                        retval += "BIGSERIAL";
                    } else {
                        retval += "BIGINT";
                    }
                } else {
                    if (length > 0) {
                        if (precision > 0 || length > 18) {
                            if ((length + precision) > 0 && precision > 0) {
                                // Numeric(Precision, Scale): Precision = total length; Scale = decimal places
                                retval += "NUMERIC(" + (length + precision) + ", " + precision + ")";
                            } else {
                                retval += "DOUBLE PRECISION";
                            }
                        } else {
                            if (length > 9) {
                                retval += "BIGINT";
                            } else {
                                if (length < 5) {
                                    retval += "SMALLINT";
                                } else {
                                    retval += "INTEGER";
                                }
                            }
                        }

                    } else {
                        retval += "DOUBLE PRECISION";
                    }
                }
                break;
            case ColumnMetaData.TYPE_STRING:
                if (length < 1 || length >= ColumnMetaData.CLOB_LENGTH) {
                    retval += "TEXT";
                } else {
                    if (!CollectionUtils.isEmpty(pks) && pks.contains(fieldname)) {
                        retval += "VARCHAR(" + length + ")";
                    } else {
                        retval += "TEXT";
                    }
                }
                break;
            case ColumnMetaData.TYPE_BINARY:
                retval += "BYTEA";
                break;
            default:
                retval += "TEXT";
                break;
        }

//        if (addCr) {
//            retval += Const.CR;
//        }

        return retval;
    }
}
