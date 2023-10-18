import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Title: B
 * @Author wangtao
 * @Date 2023/8/28 11:00
 * @description:
 */
public class B {

    public static void main(String[] args) {
        System.setProperty("HADOOP_USER_NAME", "hbase");
        String jdbcUrl = "jdbc:phoenix:node1002,node1003,node1004:2181:/hbase/master";
        try {
            Properties props = new Properties();
            props.put("phoenix.schema.isNamespaceMappingEnabled", "true");
            Connection connection = DriverManager.getConnection(jdbcUrl, props);
            Statement statement = connection.createStatement();

            String query = "select distinct TABLE_NAME from SYSTEM.\"CATALOG\"";
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Phoenix 中的数据表：");
            while (resultSet.next()) {
                String tableName = resultSet.getString("table_name");
                System.out.println(tableName);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
