package com.ot.tools.jdbc.cassandra;

import com.datastax.driver.core.*;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.List;


public class CassandraTest {

    //连接所用信息
    private static final String IP = "192.168.100.188";
    private static final int PORT = 9042;
    private static final String USERNAME = "cassandra";
    private static final String PASSWORD = "cassandra";


    private Session session;

    @Before
    public void conn() {
        InetSocketAddress address = new InetSocketAddress(IP, PORT);
        PlainTextAuthProvider authProvider = new PlainTextAuthProvider(USERNAME, PASSWORD);
        Cluster cluster = Cluster.builder().addContactPointsWithPorts(address).withAuthProvider(authProvider).build();
        session = cluster.connect("tp");
    }

    @Test
    public void createKeySpace() {
        String query = "CREATE KEYSPACE tp WITH replication "
                + "= {'class':'SimpleStrategy', 'replication_factor':1}; ";
        ResultSet rs = session.execute(query);
        System.out.println("created");
    }
    @Test
    public void query(){
        SimpleStatement simpleStatement = new SimpleStatement("select * from emp");
        simpleStatement.setFetchSize(2);
        ResultSet rs = session.execute(simpleStatement);
        List<Row> all = rs.all();
    }

    @Test
    public void query1(){
        SimpleStatement simpleStatement = new SimpleStatement("show tables");
        simpleStatement.setFetchSize(2);
        ResultSet rs = session.execute(simpleStatement);
        List<Row> all = rs.all();
    }
}
