package org.apache.dolphinscheduler.data.integration.offline.util;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.dolphinscheduler.data.integration.offline.dto.ColumnDTO;
import org.apache.dolphinscheduler.data.integration.offline.model.ColumnDescription;
import org.apache.dolphinscheduler.spi.datasource.BaseConnectionParam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Administrator
 * @create 2023/5/4 16:56
 */
public class OracleUtil {
    private static final String QUERY_ALL_TABLES = "select OWNER, TABLE_NAME from all_tables where owner = ?";

    private static final String QUERY_ALL_COLUMNS = "SELECT a.column_name, a.data_type, b.comments FROM dba_tab_columns a " +
            "LEFT JOIN all_col_comments b ON a.owner = b.owner and a.table_name = b.table_name AND a.column_name = b.column_name " +
            "WHERE a.owner = ? and a.Table_Name = ?";

    /**
     * 创建表
     *
     * @param tableName
     * @param columnList
     * @return
     */
    public static void createTable(BaseConnectionParam connectionParam, String tableName, List<ColumnDTO> columnList) throws SQLException {
        StringBuilder sb = new StringBuilder("create table if not exists ");
        sb.append(tableName).append(" (\n");
        for (ColumnDTO columnDTO : columnList) {
            sb.append(columnDTO.getName()).append(" ").append(columnDTO.getType()).append(" ");
            if (StringUtils.isNotEmpty(columnDTO.getComment())) {
                sb.append("comment '").append(columnDTO.getComment()).append("',").append("\n");
            }
        }
        String str = sb.substring(0, sb.length() - 2) + ");";
        CommonDbUtil.executeTxSqlVoid(connectionParam, str);
    }

    public static List<String> getAllTable(Connection connection, String database) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(QUERY_ALL_TABLES);
        ps.setString(1, database);
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            String tableSchema = rs.getString(1);
            String tableName = rs.getString(2);
            list.add(tableSchema + "." + tableName);
        }
        rs.close();
        ps.close();
        connection.close();
        return list;
    }

    public static List<Map<String, String>> getTableColumn(Connection connection, String tableName) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(QUERY_ALL_COLUMNS);
        String[] split = tableName.split("\\.");
        ps.setString(1, split[0]);
        ps.setString(2, split[1]);
        ResultSet rs = ps.executeQuery();
        List<Map<String, String>> list = Lists.newArrayList();
        while (rs.next()) {
            String columnName = rs.getString(1);
            String dataType = rs.getString(2);
            String columnComment = rs.getString(3);
            Map<String, String> tmp = Maps.newHashMap();
            tmp.put("name", columnName);
            tmp.put("type", dataType);
            tmp.put("comment", columnComment);
            list.add(tmp);
        }
        return list;
    }

    /**
     * 初始化对象中的注释信息
     * @param connection
     * @param tableName
     * @param ret 赋值对象
     */
    public static void initComment(Connection connection, String tableName, List<ColumnDescription> ret) throws SQLException {
        List<Map<String, String>> tableColumnList = getTableColumn(connection, tableName.replaceAll("\"", ""));
        tableColumnList.forEach(item -> {
            JSONObject jsonObject = new JSONObject(item);
            for (ColumnDescription cd : ret) {
                if (jsonObject.getString("name").equalsIgnoreCase(cd.getFieldName())) {
                    cd.setRemarks(jsonObject.getString("comment"));
                }
            }
        });
    }
}
