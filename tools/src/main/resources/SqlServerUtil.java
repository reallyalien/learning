package org.apache.dolphinscheduler.data.integration.offline.util;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.dolphinscheduler.data.integration.offline.dto.ColumnDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlServerUtil {

    private static final String DEFAULT_SCHEMA = "dbo";
    private static final String QUERY_ALL_TABLES_SQL = "SELECT TABLE_SCHEMA,TABLE_NAME from information_schema.tables where TABLE_SCHEMA not in ('dbo')";
    private static final String QUERY_SCHEMA_IS_EXISTS = "select * from INFORMATION_SCHEMA.SCHEMATA where SCHEMA_NAME=?";
    private static final String QUERY_ALL_COLUMNS_SQL = "SELECT S.name  as schema_name,\n" +
            "\t       A.name  AS table_name,\n" +
            "\t       B.name  AS column_name,\n" +
            "\t       T.name  as column_type,\n" +
            "\t       C.value AS column_description\n" +
            "\tFROM sys.schemas S\n" +
            "\t         inner join sys.tables A on S.schema_id = A.schema_id\n" +
            "\t         INNER JOIN sys.columns B ON B.object_id = A.object_id\n" +
            "\t         inner join sys.types T on T.system_type_id = B.system_type_id\n" +
            "\t         LEFT JOIN sys.extended_properties C ON C.major_id = B.object_id AND C.minor_id = B.column_id\n" +
            "\tWHERE S.name = ?\n" +
            "\t  and A.name = ?";

    /**
     * 创建表
     *
     * @param tableName
     * @param columnList
     * @return
     */
    public static String createTable(String tableName, List<ColumnDTO> columnList) {
        //查询当前schema存在不存在，不存在则先创建schema
        String[] split = tableName.split("\\.");
        if (split.length == 1 || (split.length == 2 && split[0].equals(DEFAULT_SCHEMA))) {
            //没有schema或者是dbo，sqlServer会默认建表在dbo下,直接建表即可
        } else {
            //先判断当前schema存在不存在，不存在则需要先创建schema，才可以继续建表
        }
        String schema = split[0];
        String table = split[1];
        StringBuilder sb = new StringBuilder("create table if not exists ");
        sb.append(table).append(" (\n");
        for (ColumnDTO columnDTO : columnList) {
            sb.append(columnDTO.getName()).append(" ").append(columnDTO.getType()).append(" ");
            if (StringUtils.isNotEmpty(columnDTO.getComment())) {
                sb.append("comment '").append(columnDTO.getComment()).append("',").append("\n");
            }
        }
        String str = sb.substring(0, sb.length() - 2);

        return str + ");";
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
            String columnName = rs.getString("column_name");
            String dataType = rs.getString("column_type");
            String columnDescription = rs.getString("column_description");
            Map<String, String> tmp = Maps.newHashMap();
            tmp.put("name", columnName);
            tmp.put("type", dataType);
            tmp.put("comment", columnDescription);
            list.add(tmp);
        }
        CommonDbUtil.close(connection, ps, rs);
        return list;
    }
}
