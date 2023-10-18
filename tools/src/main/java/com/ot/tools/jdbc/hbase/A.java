package com.ot.tools.jdbc.hbase;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

/**
 * @Title: A
 * @Author wangtao
 * @Date 2023/8/28 10:24
 * @description:
 */
public class A {


    public static void main(String[] args) throws IOException {
        org.apache.hadoop.conf.Configuration hconf = HBaseConfiguration.create();
        hconf.set("hbase.zookeeper.quorum", "node1002:2181,node1003:2181,node1004:2181");
        hconf.set("zookeeper.znode.parent", "/hbase/master");
        Connection connection = ConnectionFactory.createConnection(hconf);
        Table table = connection.getTable(TableName.valueOf("student"));
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes("info"));
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            StringBuilder sb = new StringBuilder();
            List<Cell> cells = result.listCells();
            for (Cell cell : cells) {
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                sb.append(value).append("-");
            }
            String value = sb.replace(sb.length() - 1, sb.length(), "").toString();
            System.out.println(value);
        }
    }
}
