package com.ot.example.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;

public class HdfsTest {

    private Configuration conf;
    private FileSystem fs;

    @Before
    public void configuration() throws URISyntaxException, IOException {
        conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.100.190:9000");
        conf.addResource(new Path("d://core-site.xml"));//可以不写这个
        fs = FileSystem.get(new URI("hdfs://192.168.100.190:9000"), conf);
        System.setProperty("HADOOP USER_NAME", "root");
    }


    /**
     * 生成新文件
     */
    @Test
    public void create() {
        try {
            fs.create(new Path("/test/3.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 所有dataNode节点
     *
     * @throws IOException
     */
    @Test
    public void listDataNode() throws IOException {
        DistributedFileSystem dfs = (DistributedFileSystem) this.fs;
        DatanodeInfo[] dataNodeStats = dfs.getDataNodeStats();
        String[] names = new String[dataNodeStats.length];
        for (int i = 0; i < dataNodeStats.length; i++) {
            names[i] = dataNodeStats[i].getHostName();
            System.out.println(names[i]);
        }
        System.out.println(dfs.getUri().toString());
    }

    @Test
    public void delete() throws IOException {
        boolean b = fs.deleteOnExit(new Path("/test/1.doc"));
        if (b) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }

    @Test
    public void exist() throws IOException {
        DistributedFileSystem dfs = (DistributedFileSystem) this.fs;
        Path homeDirectory = dfs.getHomeDirectory();

        boolean exists = dfs.exists(new Path("/test/test1.txt"));
        System.out.println(exists);
    }

    @Test
    public void upload() throws IOException {
        //window开发，本地就是window磁盘，在linux服务器上使用这个命令，指的是linux服务器硬盘
        fs.copyFromLocalFile(false, new Path("d://1.doc"), new Path("/test"));
    }
    @Test
    public void download() throws IOException {
        fs.copyToLocalFile(false,new Path("/test/test.txt"),new Path("d:"));
    }
    @Test
    public void rename() throws IOException {
        fs.rename(new Path("/test/test.txt"),new Path("/test/test1.txt"));
    }
    @Test
    public void list() throws IOException {
        DistributedFileSystem dfs = (DistributedFileSystem) this.fs;
        FileStatus[] listStatus = dfs.listStatus(new Path("/"));
        if (listStatus.length>0){
            for (FileStatus status : listStatus) {
                System.out.println(status.getPath());
            }
        }
    }

    /**
     * 取得文件块所在位置
     */
    @Test
    public void location() throws IOException {
        FileStatus fileStatus = fs.getFileStatus(new Path("/test/test1.txt"));
        BlockLocation[] fileBlockLocations = fs.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
        long modificationTime = fileStatus.getModificationTime();
        for (BlockLocation fileBlockLocation : fileBlockLocations) {
            System.out.println(fileBlockLocation);
        }
        System.out.println("最后修改的时间："+new Date(modificationTime));
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        HdfsTest hdfsTest = new HdfsTest();
        hdfsTest.configuration();
        hdfsTest.exist();
    }
}
