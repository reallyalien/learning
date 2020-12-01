package com.ot.tools.jdbc.dm;

public class A {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(" create table ").append("newTableName").append("( ").append("\r");
        sb.append("\t").append("MYSQ_ID").append(",").append("\r");
        System.out.println(sb.toString());
    }
}
