package com.ot.example.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsTest1 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        Configuration conf= new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.100.190:9000");
//        conf.addResource(new Path("d://core-site.xml"));//可以不写这个
        //这里显示设置，否则打包之后会报异常
        conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.100.190:9000"), conf);
        System.setProperty("HADOOP USER_NAME", "root");
        DistributedFileSystem dfs = (DistributedFileSystem)fs;
        boolean exists = dfs.exists(new Path("/test/test1.txt"));
        System.out.println(exists);
    }
}
