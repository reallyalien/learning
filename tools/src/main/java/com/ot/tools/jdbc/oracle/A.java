package com.ot.tools.jdbc.oracle;

public class A {

    public static final String ANONYMOUS_TABLE_NAME = "schema.table";

    public static void main(String[] args) {
        String sql = "create 'schema.table',{NAME=>'cf1'};";
        sql.replaceAll(ANONYMOUS_TABLE_NAME, "xxx");
    }
}
