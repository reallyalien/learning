package org.apache.dolphinscheduler.data.integration.offline.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dolphinscheduler.data.integration.offline.exception.TypeNotSupportException;
import org.apache.dolphinscheduler.data.integration.offline.model.ColumnDescription;
import org.apache.dolphinscheduler.data.integration.offline.model.ColumnMetaData;
import org.apache.dolphinscheduler.plugin.datasource.api.utils.PasswordUtils;
import org.apache.dolphinscheduler.spi.datasource.BaseConnectionParam;
import org.apache.dolphinscheduler.spi.enums.DbType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

;

@Slf4j
public class CommonDbUtil {

    /**
     * 获取表主键
     *
     * @param connection
     * @param catalog
     * @param schema
     * @param table
     * @return
     */
    public static List<String> getTablePrimaryKeys(Connection connection, String catalog, String schema, String table, Boolean isClose) {
        List<String> pks = new ArrayList<>();
        ResultSet primaryKeys = null;
        try {
            primaryKeys = connection.getMetaData().getPrimaryKeys(catalog, schema, table);
            while (primaryKeys.next()) {
                String name = primaryKeys.getString("COLUMN_NAME");
                pks.add(name);
            }
            return pks;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null == isClose || isClose) {
                close(connection, null, primaryKeys);
            }
        }
    }

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
                if (StringUtils.isNotEmpty(tableSchem) && excludeSchemas.contains(tableSchem)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null == isClose || isClose) {
                close(connection, null, rs);
            }
        }
        return list;
    }

    /**
     * 获取表列的元数据信息
     *
     * @param dbType     数据库类型
     * @param connection 数据库连接对象
     * @param tableName  查询的表名称
     *                   eg:test.sink
     * @param isClose    查询完毕之后是否关闭数据库连接
     * @return
     */
    public static List<ColumnDescription> getTableColumnMeta(DbType dbType, Connection connection, String tableName, Boolean isClose) {
        tableName = formatTableName(dbType, tableName);
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
                cd.setDbtype(dbType);

                ret.add(cd);
            }
            switch (dbType) {
                case ORACLE:
                    OracleUtil.initComment(connection, tableName, ret);
                    break;
                default:
                    String[] split = tableName.split("\\.");
                    String s1 = split[0];
                    String s2 = split[1];
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
                    break;
            }
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            if (null == isClose || isClose) {
                try {
                    close(connection, pstmt, set, rs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String formatTableName(DbType dbType, String tableName) {
        switch (dbType) {
            case ORACLE:
                String[] split = tableName.split("\\.");
                String s1 = split[0];
                String s2 = split[1];
                s2 = "\"" + s2 + "\"";
                return tableName = s1 + "." + s2;
            default:
                return tableName;
        }
    }

    /**
     * 给目标字段添加限制长度或精度
     * 这里考虑的不全面，后期要不断补充
     *
     * @param originalType
     * @param columnDescription
     * @return
     */
    public static String supplement(DbType dbType, String originalType, ColumnDescription columnDescription) {
        int displaySize = columnDescription.getDisplaySize();
        int precisionSize = columnDescription.getPrecisionSize();
        int scaleSize = columnDescription.getScaleSize();
        switch (originalType) {
            case "VARCHAR":
                return "VARCHAR(" + displaySize + ")";
            case "NUMERIC":
            case "DECIMAL":
                return originalType + "(" + precisionSize + "," + scaleSize + ")";
            default:
                return originalType;
        }
    }

    /**
     * 关闭数据库连接
     *
     * @param connection 数据库连接对象
     * @param ps         数据库预编译对象
     * @param rs         结果集数组
     */
    public static void close(Connection connection, PreparedStatement ps, ResultSet... rs) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (rs != null && rs.length > 0) {
                for (ResultSet r : rs) {
                    if (r != null) {
                        r.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行事务sql
     *
     * @param baseConnectionParam 数据库查询连接参数
     * @param sql                 要执行的事务sql
     */
    public static void executeTxSqlVoid(BaseConnectionParam baseConnectionParam, String sql) throws SQLException {
        log.info("执行事务sql:【{}】", sql);
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = DriverManager.getConnection(
                    baseConnectionParam.getJdbcUrl(),
                    baseConnectionParam.getUser(),
                    PasswordUtils.decodePassword(baseConnectionParam.getPassword())
            );
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(sql);
            ps.execute();
            connection.commit();
        } catch (SQLException throwables) {
            if (null != connection) {
                connection.rollback();
            }
        } finally {
            close(connection, ps, null);
        }
    }

    /**
     * 将源目标数据源字段类型转换为目标数据库字段类型
     *
     * @param dbType
     * @param columnMetaData
     * @return
     */
    public static Map<String, String> convert(DbType dbType, ColumnMetaData columnMetaData) {
        String fieldDefinition = null;
        switch (dbType) {
            case MYSQL:
                fieldDefinition = MysqlUtil.getFieldDefinition(columnMetaData, Collections.emptyList(), false, false);
                break;
            case POSTGRESQL:
                fieldDefinition = PostgreSqlUtil.getFieldDefinition(columnMetaData, Collections.emptyList(), false, false);
                break;
            default:
                throw new TypeNotSupportException();
        }
        if (StringUtils.isNotEmpty(fieldDefinition)) {
            if (fieldDefinition.contains("(")) {
                fieldDefinition = fieldDefinition.substring(0, fieldDefinition.indexOf("("));
            }
            if (fieldDefinition.contains(" ")) {
                int i = fieldDefinition.lastIndexOf(" ");
                fieldDefinition = fieldDefinition.substring(i + 1);
            }
        }
        Map<String, String> map = Maps.newHashMap();
        map.put("fieldName", columnMetaData.getName());
        map.put("convertType", fieldDefinition);
        return map;
    }
}
