import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.ByteArrayComparable;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.shaded.protobuf.generated.FilterProtos;
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
        System.setProperty("HADOOP_USER_NAME", "hbase");
        org.apache.hadoop.conf.Configuration hconf = HBaseConfiguration.create();
        hconf.set("hbase.zookeeper.quorum", "node1002:2181,node1003:2181,node1004:2181");
        hconf.set("zookeeper.znode.parent", "/hbase/master");
        Connection connection = ConnectionFactory.createConnection(hconf);
        Table table = connection.getTable(TableName.valueOf("from_mysql_zdy_zz_user"));
        Scan scan = new Scan();
        SingleColumnValueFilter filter = new SingleColumnValueFilter(
                Bytes.toBytes("cf1"),
                Bytes.toBytes("id"),
                CompareOperator.EQUAL,
                Bytes.toBytes("280")
        );
        FilterList filterList = new FilterList();
        filterList.addFilter(filter);
        scan.setFilter(filterList);
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            StringBuilder sb = new StringBuilder();
            List<Cell> cells = result.listCells();
            for (Cell cell : cells) {
                String family = Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength());
                String qualifier = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                sb.append(value).append("*******");
            }
            String value = sb.replace(sb.length() - 1, sb.length(), "").toString();
            System.out.println(value);
        }
    }
}
