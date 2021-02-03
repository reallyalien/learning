package com.ot.example.parquet;

import com.ot.example.hive.Hive;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.column.ParquetProperties;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroup;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.format.converter.ParquetMetadataConverter;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.hadoop.metadata.ParquetMetadata;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestParquetWriter1 {

    private static Logger logger = LoggerFactory.getLogger(TestParquetWriter1.class);

    private static final String writeSchema = "message example {\n" +
            "required int32 id;\n" +
            "required binary name;\n" +
            "required binary sex;\n" +
            "required int32 age;\n" +
            "required binary department;\n" +

            "}";
    static MessageType schema = MessageTypeParser.parseMessageType(writeSchema);

    /**
     * 创建时间：2017-8-3
     * 创建者：meter
     * 返回值类型：void
     *
     * @描述：输出MessageType
     */
    public static void testParseSchema() {
        logger.info(schema.toString());
    }

    /**
     * 创建时间：2017-8-3
     * 创建者：meter
     * 返回值类型：void
     *
     * @throws Exception
     * @描述：获取parquet的Schema
     */
    public static void testGetSchema() throws Exception {
        Configuration configuration = new Configuration();
        // windows 下测试入库impala需要这个配置
        System.setProperty("hadoop.home.dir",
                "E:\\mvtech\\software\\hadoop-common-2.2.0-bin-master");
        ParquetMetadata readFooter = null;
        Path parquetFilePath = new Path("file:///E:/mvtech/work/isms_develop/src/org/meter/parquet/2017-08-02-10_91014_DPI0801201708021031_470000.parq");
        readFooter = ParquetFileReader.readFooter(configuration,
                parquetFilePath, ParquetMetadataConverter.NO_FILTER);
        MessageType schema = readFooter.getFileMetaData().getSchema();
        logger.info(schema.toString());
    }



    /**
     * 创建时间：2017-8-3
     * 创建者：meter
     * 返回值类型：void
     *
     * @throws IOException
     * @描述：测试读parquet文件
     */
    private static void testParquetReader() throws IOException {
        Path file = new Path(
                "file:///C:\\Users\\meir\\Desktop\\linuxtetdir\\logtxt\\test.parq");
        ParquetReader.Builder<Group> builder = ParquetReader.builder(new GroupReadSupport(), file);

        ParquetReader<Group> reader = builder.build();
        SimpleGroup group = (SimpleGroup) reader.read();
        logger.info("schema:" + group.getType().toString());
        logger.info("idc_id:" + group.getString(1, 0));
    }

    /**
     * 创建时间：2017-8-2 创建者：meter 返回值类型：void
     *
     * @param args
     * @throws Exception
     * @描述：
     */
    public static void main(String[] args) throws Exception {
        String fileName = "5.parquest";
        //testGetSchema();
        //testParseSchema();
        //=================================测试hive导入数据=================================
        long start = System.currentTimeMillis();
        testParquetWriter(fileName);
        long end1 = System.currentTimeMillis();
        System.out.println("写hdfs数据成功，耗时：" + (end1 - start) / 1000 + "秒");
        Connection connection = Hive.getConnection();
        PreparedStatement ps = connection.prepareStatement("load data inpath '/test/5.parquest' into table student_bck");
        ps.execute();
        long end2 = System.currentTimeMillis();
        System.out.println("写hive数据成功，耗时：" + (end2 - end1) / 1000 + "秒");
//        testParquetReader();
    }
    /**
     * 创建时间：2017-8-3
     * 创建者：meter
     * 返回值类型：void
     *
     * @throws IOException
     * @描述：测试写parquet文件
     */
    private static void testParquetWriter(String fileName) throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://192.168.100.190:9000");
        configuration.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        Path file = new Path(
                "/test/" + fileName);
        ExampleParquetWriter.Builder builder = ExampleParquetWriter
                .builder(file)
                .withWriteMode(ParquetFileWriter.Mode.CREATE)
                .withWriterVersion(ParquetProperties.WriterVersion.PARQUET_1_0)
                .withCompressionCodec(CompressionCodecName.SNAPPY)
                .withConf(configuration)
                .withType(schema);
        /*
         * file, new GroupWriteSupport(), CompressionCodecName.SNAPPY, 256 *
         * 1024 * 1024, 1 * 1024 * 1024, 512, true, false,
         * ParquetProperties.WriterVersion.PARQUET_1_0, conf
         */
        ParquetWriter<Group> writer = builder.build();
        SimpleGroupFactory groupFactory = new SimpleGroupFactory(schema);
        Object[] data = {10000,"javaScript", "known",99,"aaaaaa"};
        for (int i = 0; i < 10_0000; i++) {
            writer.write(groupFactory.newGroup()
                    .append("id", (Integer) data[0])
                    .append("name", (String) data[1])
                    .append("sex", (String) data[2])
                    .append("age", (Integer) data[3])
                    .append("department", (String) data[4])


            );
        }
        writer.close();
    }

}
