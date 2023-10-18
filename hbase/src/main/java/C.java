import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.ColumnCountGetFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Title: A
 * @Author wangtao
 * @Date 2023/8/28 10:24
 * @description:
 */
public class C {


    public static void main(String[] args) throws IOException {
        System.setProperty("HADOOP_USER_NAME", "hbase");
        org.apache.hadoop.conf.Configuration hconf = HBaseConfiguration.create();
        hconf.set("hbase.zookeeper.quorum", "node1002:2181,node1003:2181,node1004:2181");
        hconf.set("zookeeper.znode.parent", "/hbase/master");
        Connection connection = ConnectionFactory.createConnection(hconf);

        TableName orderInfo = TableName.valueOf("ORDER_INFO");
        Table table = connection.getTable(TableName.valueOf("ORDER_INFO"));





        //获取所有的列簇
        Admin admin = connection.getAdmin();
        TableDescriptor descriptor = admin.getDescriptor(orderInfo);



        ColumnFamilyDescriptor[] columnFamilies = descriptor.getColumnFamilies();
        for (ColumnFamilyDescriptor columnFamily : columnFamilies) {
            String nameAsString = columnFamily.getNameAsString();
            System.out.println(nameAsString);
        }
    }
}
