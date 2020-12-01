package com.ot.bio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StreamDemo {
    @Test
    public void read() throws IOException {
        FileInputStream is = new FileInputStream("E:/develop/Neo4j/neo4j.conf");
        int i = is.available();
        SecurityManager securityManager = System.getSecurityManager();
        System.out.println(i);
    }
}
