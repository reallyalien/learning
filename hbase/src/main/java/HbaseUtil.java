import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Title: HbaseUtil
 * @Author wangtao
 * @Date 2023/8/28 15:44
 * @description:
 */
public class HbaseUtil {

    public static Connection getConn() throws IOException {
        System.setProperty("HADOOP_USER_NAME", "hbase");
        org.apache.hadoop.conf.Configuration hconf = HBaseConfiguration.create();
        hconf.set("hbase.zookeeper.quorum", "node1002:2181,node1003:2181,node1004:2181");
        hconf.set("zookeeper.znode.parent", "/hbase/master");
        Connection connection = ConnectionFactory.createConnection(hconf);
        return connection;
    }

    public static Connection getConn1() throws IOException {
        System.setProperty("HADOOP_USER_NAME", "hbase");
        org.apache.hadoop.conf.Configuration hconf = HBaseConfiguration.create();
        hconf.set("hbase.zookeeper.quorum", "node1002,node1003,node1004");
        hconf.set("zookeeper.znode.parent", "/hbase/master");
        hconf.set("hbase.zookeeper.property.clientPort", "2181");
        Connection connection = ConnectionFactory.createConnection(hconf);
        return connection;
    }

    /**
     * 获取指定表下面的所有列簇
     *
     * @param connection
     * @param table
     * @return
     * @throws IOException
     */
    public static List<String> getTables(Connection connection) throws IOException {
        Admin admin = connection.getAdmin();
        TableName[] tableNames = admin.listTableNames();
        List<String> collect = Arrays.stream(tableNames).map(TableName::getNameAsString).filter(e -> !e.startsWith("SYSTEM")).collect(Collectors.toList());
        System.out.println(collect);
        return collect;
    }

    /**
     * 获取指定表下面的所有列簇
     *
     * @param connection
     * @param table
     * @return
     * @throws IOException
     */
    public static List<String> getFamilies(Connection connection, String table) throws IOException {
        Admin admin = connection.getAdmin();
        TableName tableName = TableName.valueOf(table);
        TableDescriptor descriptor = admin.getDescriptor(tableName);
        ColumnFamilyDescriptor[] columnFamilies = descriptor.getColumnFamilies();
        List<String> families = new ArrayList<>(columnFamilies.length);
        for (ColumnFamilyDescriptor columnFamily : columnFamilies) {
            String family = columnFamily.getNameAsString();
            families.add(family);
        }
        return families;
    }

    /**
     * 获取指定表下、指定列簇下的所有列
     *
     * @param connection
     * @param tableName
     * @param family
     * @return
     */
    public static List<String> getQualifiers(Connection connection, String tableName, String family) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        scan.setLimit(1);
        ResultScanner scanner = table.getScanner(scan);
        List<String> qualifiers = new ArrayList<>();
        for (Result result : scanner) {
            List<Cell> cells = result.listCells();
            for (Cell cell : cells) {
                String qualifier = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                qualifiers.add(family + ":" + qualifier);
            }
        }
        return qualifiers;
    }


    public static void main(String[] args) throws IOException {
        Connection conn = getConn1();

        List<String> tables = getTables(conn);
        System.out.println();


        String tableName = "zdy_zz_user_copy";
        List<String> families = getFamilies(conn, tableName);
        List<String> result = new ArrayList<>();
        for (String family : families) {
            List<String> qualifiers = getQualifiers(conn, tableName, family);
            result.addAll(qualifiers);
        }
        System.out.println(result);
    }
}
