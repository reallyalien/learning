package org.apache.dolphinscheduler.data.integration.offline.util;


import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dolphinscheduler.data.integration.offline.dto.ColumnDTO;
import org.apache.dolphinscheduler.data.integration.offline.model.ColumnMetaData;
import org.apache.dolphinscheduler.spi.datasource.BaseConnectionParam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class MysqlUtil {

    private static final String QUERY_ALL_TABLES = "SELECT TABLE_SCHEMA,TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=?";

    private static final String QUERY_ALL_COLUMNS = "SELECT COLUMN_NAME,DATA_TYPE,COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA=? AND TABLE_NAME=?";

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
        log.info("创建表：【{}】成功", tableName);
    }

    public static List<String> getAllTable(Connection connection, String database) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(QUERY_ALL_TABLES);
        ps.setString(1, database);
        ResultSet rs = ps.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            String tableSchema = rs.getString("TABLE_SCHEMA");
            String tableName = rs.getString("TABLE_NAME");
            list.add(tableSchema + "." + tableName);
        }
        log.info("查询当前数据源下所有的表：【{}】", JSON.toJSONString(list));
        CommonDbUtil.close(connection, ps, rs);
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
            String columnName = rs.getString("COLUMN_NAME");
            String columnType = rs.getString("COLUMN_TYPE");
            String columnComment = rs.getString("COLUMN_COMMENT");
            Map<String, String> tmp = Maps.newHashMap();
            tmp.put("name", columnName);
            tmp.put("type", columnType);
            tmp.put("comment", columnComment);
            list.add(tmp);
        }
        CommonDbUtil.close(connection, ps, rs);
        log.info("查询当前数据源下指定表的列：【{}】", JSON.toJSONString(list));
        return list;
    }


    public static String getFieldDefinition(ColumnMetaData v, List<String> pks, boolean useAutoInc, boolean addCr) {
        String fieldname = v.getName();
        int length = v.getLength();
        int precision = v.getPrecision();
        int type = v.getType();

        String retval = " `" + fieldname + "` ";

        switch (type) {
            case ColumnMetaData.TYPE_TIMESTAMP:
                retval += "DATETIME";
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
                        retval += "BIGINT AUTO_INCREMENT NOT NULL";
                    } else {
                        retval += "BIGINT NOT NULL";
                    }
                } else {
                    // Integer values...
                    if (precision == 0) {
                        if (length > 9) {
                            if (length < 19) {
                                // can hold signed values between -9223372036854775808 and 9223372036854775807
                                // 18 significant digits
                                retval += "BIGINT";
                            } else {
                                retval += "DECIMAL(" + length + ")";
                            }
                        } else {
                            retval += "INT";
                        }
                    } else {
                        // Floating point values...
                        if (length > 15) {
                            retval += "DECIMAL(" + length;
                            if (precision > 0) {
                                retval += ", " + precision;
                            }
                            retval += ")";
                        } else {
                            // A double-precision floating-point number is accurate to approximately 15
                            // decimal places.
                            // http://mysql.mirrors-r-us.net/doc/refman/5.1/en/numeric-type-overview.html
                            retval += "DOUBLE";
                        }
                    }
                }
                break;
            case ColumnMetaData.TYPE_STRING:
                if (length > 0) {
                    if (length == 1) {
                        retval += "CHAR(1)";
                    } else if (length < 256) {
                        retval += "VARCHAR(" + length + ")";
                    } else if (!CollectionUtils.isEmpty(pks) && pks.contains(fieldname)) {
                        /*
                         * MySQL5.6中varchar字段为主键时最大长度为254,例如如下的建表语句在MySQL5.7下能通过，但在MySQL5.6下无法通过：
                         *	create table `t_test`(
                         *	`key` varchar(1024) binary,
                         *	`val` varchar(1024) binary,
                         *	primary key(`key`)
                         * );
                         */
                        retval += "VARCHAR(254) BINARY";
                    } else if (length < 65536) {
                        retval += "TEXT";
                    } else if (length < 16777216) {
                        retval += "MEDIUMTEXT";
                    } else {
                        retval += "LONGTEXT";
                    }
                } else {
                    retval += "TINYTEXT";
                }
                break;
            case ColumnMetaData.TYPE_BINARY:
                retval += "LONGBLOB";
                break;
            default:
                retval += " LONGTEXT";
                break;
        }

        if (addCr) {
//            retval += Const.CR;
        }

        return retval;
    }

}
