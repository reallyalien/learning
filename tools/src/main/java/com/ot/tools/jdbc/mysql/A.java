package com.ot.tools.jdbc.mysql;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class A {


    private static String createTableSql(String tableName, List<ColumnDTO> columnList) {
        StringBuilder sb = new StringBuilder("create table if not exists ");
        sb.append(tableName).append(" (\n");
        for (ColumnDTO columnDTO : columnList) {
            sb.append(columnDTO.getName()).append(" ").append(columnDTO.getType()).append(" ");
            if (StringUtils.isNotEmpty(columnDTO.getComment())) {
                sb.append("comment '").append(columnDTO.getComment()).append("',").append("\n");
            }
        }
        String str = sb.substring(0, sb.length() - 2);
        return str + ");";
    }


    public static void main(String[] args) {
        List<ColumnDTO> columnDTOS = new ArrayList<>();
        ColumnDTO columnDTO1 = ColumnDTO.builder().name("id").comment("主键").type("int").build();
        ColumnDTO columnDTO2 = ColumnDTO.builder().name("name").comment("名称").type("varchar").build();
        columnDTOS.add(columnDTO1);
        columnDTOS.add(columnDTO2);
        String xxx = createTableSql("xxx", columnDTOS);
        System.out.println(xxx);
    }
}
