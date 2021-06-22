package com.ot.tools.jdbc.clickhouse;

import com.github.housepower.jdbc.BalancedClickhouseDataSource;
import com.github.housepower.jdbc.ClickHouseConnection;
import com.github.housepower.jdbc.settings.SettingKey;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class ClickHouse {

    //连接所用信息
    private static final String IP = "192.168.100.190";
    //使用8123端口连接不成功，提示是http端口问题
    private static final int PORT = 8123;
    private static final String USERNAME = "default";
    private static final String PASSWORD = "Admin123";
    private static final String URL = "jdbc:clickhouse://192.168.100.190:9001";
    private DataSource dataSource;

    @Before
    public void before() throws SQLException {
        Map<SettingKey, Object> map = new HashMap<>();
        map.put(SettingKey.user, USERNAME);
        map.put(SettingKey.password, PASSWORD);
        dataSource = new BalancedClickhouseDataSource(URL, map);
    }

    @Test
    public void conn() throws SQLException {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet schemas = metaData.getSchemas();
        while (schemas.next()){
            String tableSchema = schemas.getString("TABLE_SCHEM");
            System.out.println(tableSchema);
        }
    }
}
