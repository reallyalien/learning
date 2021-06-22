package com.ot.tools.jdbc.sqlserver;

import com.microsoft.sqlserver.jdbc.SQLServerResultSet;
import org.neo4j.jdbc.bolt.BoltNeo4jResultSet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlServerTest {

    public static final String url = "jdbc:sqlserver://192.168.100.188:1433";
    public static final String username = "sa";
    public static final String password = "Admin123";

    private final static String BUFFER_LENGTH = "BUFFER_LENGTH";
    private final static String CHAR_OCTET_LENGTH = "CHAR_OCTET_LENGTH";
    private final static String COLUMN_DEF = "COLUMN_DEF";
    private final static String COLUMN_NAME = "COLUMN_NAME";
    private final static String COLUMN_SIZE = "COLUMN_SIZE";
    private final static String DATA_TYPE = "DATA_TYPE";
    private final static String DECIMAL_DIGITS = "DECIMAL_DIGITS";
    private final static String IS_NULLABLE = "IS_NULLABLE";
    private final static String NULLABLE = "NULLABLE";
    private final static String NUM_PREC_RADIX = "NUM_PREC_RADIX";
    private final static String ORDINAL_POSITION = "ORDINAL_POSITION";
    private final static String REMARKS = "REMARKS";
    private final static String SQL_DATA_TYPE = "SQL_DATA_TYPE";
    private final static String SQL_DATETIME_SUB = "SQL_DATETIME_SUB";
    private final static String TABLE_CAT = "TABLE_CAT";
    private final static String TABLE_NAME = "TABLE_NAME";
    private final static String TABLE_SCHEM = "TABLE_SCHEM";
    private final static String TYPE_NAME = "TYPE_NAME";

    private final static String[] getColumnsColumnNames = {/* 1 */ TABLE_CAT, /* 2 */ TABLE_SCHEM, /* 3 */ TABLE_NAME, /* 4 */ COLUMN_NAME,
            /* 5 */ DATA_TYPE, /* 6 */ TYPE_NAME, /* 7 */ COLUMN_SIZE, /* 8 */ BUFFER_LENGTH, /* 9 */ DECIMAL_DIGITS, /* 10 */ NUM_PREC_RADIX,
            /* 11 */ NULLABLE, /* 12 */ REMARKS, /* 13 */ COLUMN_DEF, /* 14 */ SQL_DATA_TYPE, /* 15 */ SQL_DATETIME_SUB, /* 16 */ CHAR_OCTET_LENGTH,
            /* 17 */ ORDINAL_POSITION, /* 18 */ IS_NULLABLE};

    private static Connection connection;

    public static void getConnection() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public static List<String> findAllSchema() throws SQLException {
        List<String> list = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet schemas = metaData.getSchemas();
        while (schemas.next()) {
            String tableSchema = schemas.getString("TABLE_SCHEM");
            list.add(tableSchema);
        }
        return list;
    }


    public static void main(String[] args) throws SQLException {
        getConnection();

        ResultSet columns = (SQLServerResultSet) connection.getMetaData().getColumns(null,
                "dbo", "check88", null);
        for (int i = 0; i < getColumnsColumnNames.length; i++) {
            System.out.printf("%-20s", getColumnsColumnNames[i].toLowerCase());
        }
        System.out.println();
        while (columns.next()) {
            for (int i = 1; i < 20; i++) {
                String string = columns.getString(i);
                System.out.printf("%-20s", string);
            }
            System.out.println();
        }
    }
}
