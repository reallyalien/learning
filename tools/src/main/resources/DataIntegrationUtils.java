package org.apache.dolphinscheduler.data.integration.offline.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dolphinscheduler.common.utils.JSONUtils;
import org.apache.dolphinscheduler.data.integration.offline.base.ReaderWriter;
import org.apache.dolphinscheduler.data.integration.offline.conf.JobConfiguration;
import org.apache.dolphinscheduler.data.integration.offline.dto.ColumnDTO;
import org.apache.dolphinscheduler.data.integration.offline.exception.TypeNotSupportException;
import org.apache.dolphinscheduler.data.integration.offline.model.ColumnDescription;
import org.apache.dolphinscheduler.data.integration.offline.writer.HbaseWriter;
import org.apache.dolphinscheduler.plugin.datasource.influxdb.param.InfluxDBConnectionParam;
import org.apache.dolphinscheduler.plugin.datasource.influxdb.util.InfluxDbUtil;
import org.apache.dolphinscheduler.spi.datasource.BaseConnectionParam;
import org.apache.dolphinscheduler.spi.enums.DbType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

;

@Slf4j
public class DataIntegrationUtils {

    public static Pattern compile = Pattern.compile("[A-Z]");

    /**
     * 构建 chunjun 任务执行的json 字符串
     *
     * @param reader  reader
     * @param writer  writer
     * @param setting chunjun task 通用配置 见 https://dtstack.github.io/chunjun/documents/ChunJun%E9%80%9A%E7%94%A8%E9%85%8D%E7%BD%AE%E8%AF%A6%E8%A7%A3
     * @return
     */
    public static String json(ReaderWriter reader, ReaderWriter writer, JobConfiguration.Setting setting) {
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> job = Maps.newHashMap();
        Map<String, Object>[] content = new HashMap[1];
        Map<String, Object> content0 = Maps.newHashMap();
        Map<String, Object> readerMap = Maps.newHashMap();
        Map<String, Object> writerMap = Maps.newHashMap();
        content[0] = content0;
        String name = "name";
        String parameter = "parameter";
        readerMap.put(name, reader.getType());
        readerMap.put(parameter, reader.build());
        writerMap.put(name, writer.getType());
        writerMap.put(parameter, writer.build());
        if (StringUtils.equals(writer.getType(), "hbasewriter")) {
            writerMap.put("table", HbaseWriter.queryTable());
        }
        content0.put("reader", readerMap);
        content0.put("writer", writerMap);
        job.put("content", content);
        job.put("setting", setting);
        result.put("job", job);
        return JSONUtils.toJsonString(result);
    }

    /**
     * 驼峰格式转点
     * addressName->address.name
     *
     * @param str
     * @return
     */
    public static String cameCastPoint(String str) {
        Matcher matcher = compile.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "." + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 创建表
     *
     * @param connectionParam
     * @param dbType
     * @param tableName
     * @param columnList
     * @param
     * @throws SQLException
     */
    public static void createTable(BaseConnectionParam connectionParam, DbType dbType, String tableName, List<ColumnDTO> columnList) throws SQLException {
        switch (dbType) {
            case MYSQL:
                MysqlUtil.createTable(connectionParam, tableName, columnList);
                break;
            case POSTGRESQL:
                PostgreSqlUtil.createTable(connectionParam, tableName, columnList);
                break;
            case SQLSERVER:
                SqlServerUtil.createTable(tableName, columnList);
                break;
            case ORACLE:
                OracleUtil.createTable(connectionParam, tableName, columnList);
                break;
            default:
                throw new TypeNotSupportException();
        }
    }


    /**
     * 获取当前数据源的所有表
     *
     * @param dbType          数据源类型
     * @param connection      数据库连接对象
     * @param connectionParam 连接参数
     * @param isClose         查询完成之后是否关闭数据库连接
     * @return eg:[test.a, test.b]
     * @throws SQLException
     */
    public static List<String> getTables2(DbType dbType, Connection connection, BaseConnectionParam connectionParam, Boolean isClose) throws SQLException {
        List<String> tables = null;
        String catalog = connectionParam.getDatabase();
        switch (dbType) {
            case MYSQL:
                tables = CommonDbUtil.getAllTables(connection, catalog, catalog, Collections.emptyList(), isClose);
                break;
            case POSTGRESQL:
                tables = CommonDbUtil.getAllTables(connection, catalog, null, Collections.emptyList(), isClose);
                break;
            case SQLSERVER:
                tables = CommonDbUtil.getAllTables(connection, catalog, null, Arrays.asList("dbo", "sys", "INFORMATION_SCHEMA"), isClose);
                break;
            case ORACLE:
                tables = OracleUtil.getAllTable(connection, catalog);
                break;
            case INFLUXDB:
                InfluxDBConnectionParam influxDBConnectionParam = (InfluxDBConnectionParam) connectionParam;
                InfluxDbUtil influxDbUtil = new InfluxDbUtil(influxDBConnectionParam.getUser(),
                        influxDBConnectionParam.getPassword(),
                        influxDBConnectionParam.getAddress(),
                        influxDBConnectionParam.getDatabase());
                tables = influxDbUtil.queryTables();
                break;
            default:
                throw new TypeNotSupportException();
        }
        return tables;
    }

    /**
     * 获取指定数据源的表的所有列，以及列的详细信息
     *
     * @param dbType     数据库类型
     * @param connection 数据库连接对象
     * @param tableName  要查询的表名称
     *                   eg:test.sink,对于mysql来讲，schema即为数据库名称，对于pg，sqlServer，oracle，schema就是数据库真实存在的schema
     * @return
     * @throws SQLException
     */
    public static List<ColumnDescription> getTableColumns2(DbType dbType, Connection connection, String tableName) throws SQLException {
        switch (dbType) {
            case MYSQL:
            case POSTGRESQL:
            case SQLSERVER:
            case ORACLE:
            default:
                return CommonDbUtil.getTableColumnMeta(dbType, connection, tableName, null);
        }
    }

    /**
     * 获取指定表的主键字段
     *
     * @param dbType     数据库类型
     * @param connection 数据库连接对象
     * @param tableName  要查询的表名称
     *                   eg:test.sink,对于mysql来讲，schema即为数据库名称，对于pg，sqlServer，oracle，schema就是数据库真实存在的schema
     * @return
     * @throws SQLException
     */
    public static List<String> getTablePrimaryKeys(DbType dbType, Connection connection, String tableName) throws SQLException {
        String[] split = tableName.split("\\.");
        switch (dbType) {
            case MYSQL:
            case POSTGRESQL:
            case SQLSERVER:
            case ORACLE:
            default:
                return CommonDbUtil.getTablePrimaryKeys(connection, null, split[0], split[1], null);
        }
    }

    @Deprecated
    public static List<String> getTables(DbType dbType, Connection connection, String database) throws SQLException {
        List<String> tables = null;
        switch (dbType) {
            case MYSQL:
                tables = MysqlUtil.getAllTable(connection, database);
                break;
            case POSTGRESQL:
                tables = PostgreSqlUtil.getAllTable(connection, database);
                break;
            case SQLSERVER:
                tables = SqlServerUtil.getAllTable(connection, database);
                break;
            case ORACLE:
                tables = OracleUtil.getAllTable(connection, database);
                break;
            default:
                throw new TypeNotSupportException();
        }
        return tables;
    }

    @Deprecated
    public static List<Map<String, String>> getTableColumns(DbType dbType, Connection connection, String tableName) throws SQLException {
        List<Map<String, String>> tables = null;
        switch (dbType) {
            case MYSQL:
                tables = MysqlUtil.getTableColumn(connection, tableName);
                break;
            case POSTGRESQL:
                tables = PostgreSqlUtil.getTableColumn(connection, tableName);
                break;
            case SQLSERVER:
                tables = SqlServerUtil.getTableColumn(connection, tableName);
                break;
            case ORACLE:
                tables = OracleUtil.getTableColumn(connection, tableName);
                break;
            default:
                throw new TypeNotSupportException();
        }
        return tables;
    }
}
