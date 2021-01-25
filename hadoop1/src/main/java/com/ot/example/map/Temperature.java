package com.ot.example.map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.util.Iterator;

/**
 * 统计气温变化
 * 比如：2010 0123 25表示在2010年01月23日的气温为25度。现在要求使用MapReduce，计算每一年出现过的最大气温。
 *
 *
 * map-->combine->Partitioner-->->sort-->reduce
 */
public class Temperature {

    /**
     * 四个泛型类型分别代表：
     * KeyIn        Mapper的输入数据的Key，这里是每行文字的起始位置（0,11,...）
     * ValueIn      Mapper的输入数据的Value，这里是每行文字
     * KeyOut       Mapper的输出数据的Key，这里是每行文字中的“年份”
     * ValueOut     Mapper的输出数据的Value，这里是每行文字中的“气温”
     * <p>
     * 每一行数据都会mapper，故而产生多个键值对，然后对键相同的进行分区，然后排序，有reduce调用reduce，没有则写入到hdfs文件当中
     */
    static class TempMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            System.out.println("mapper 之前：" + key + "," + value);
            String line = value.toString();
            String year = line.substring(0, 4);
            int temperature = Integer.parseInt(line.substring(8));
            context.write(new Text(year), new IntWritable(temperature));
            System.out.println("mapper 输出：" + year + "," + temperature);
        }


        //下面的方法只会调用一次，而map方法会给每个键值对调用一次
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            //在mapper实例化之前做一些工作，比如全局配置，打开一个数据库连接，打开文件等
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            //关闭连接，关闭资源等操作
        }
    }

    /**
     * reduce会复制多个mapper的输出，把复制到reduce的本地数据进行合并，把分散的数据合成一个大数据，然后排序，调用reduce方法，再将
     * 结果的键值对写入到hdfs文件
     * 四个泛型类型分别代表：
     * KeyIn        Reducer的输入数据的Key，这里是每行文字中的“年份”
     * ValueIn      Reducer的输入数据的Value，这里是每行文字中的“气温”
     * KeyOut       Reducer的输出数据的Key，这里是不重复的“年份”
     * ValueOut     Reducer的输出数据的Value，这里是这一年中的“最高气温”
     */
    static class TempReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        /**
         * 数据样本：2020,12,34,23,09
         *
         * @param key
         * @param values
         * @param
         * @param
         * @throws IOException
         */
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            StringBuilder sb = new StringBuilder();
            int maxValue = Integer.MIN_VALUE;
            Iterator<IntWritable> iterator = values.iterator();
            while (iterator.hasNext()) {
                int i = iterator.next().get();
                maxValue = Math.max(maxValue, i);
                sb.append(i).append(",");
            }
            System.out.println("reduce 之前:" + key + "," + sb.toString());
            context.write(key, new IntWritable(maxValue));
            System.out.println("reduce 之后:" + key + "," + maxValue);
        }

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //输入路径
        String dst = "hdfs://192.168.100.190:9000/test/hadoop-map.txt";
        //输出路径,必须是不存在的，空文件夹也不行
        String out = "hdfs://192.168.100.190:9000/test/out";
        Configuration hadoopConfig = new Configuration();
//        hadoopConfig.addResource(new Path("d://core-site.xml"));
        hadoopConfig.set("fs.defaultFS", "hdfs://192.168.100.190:9000");
        hadoopConfig.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        hadoopConfig.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        System.setProperty("HADOOP USER_NAME", "root");
        Job job = Job.getInstance(hadoopConfig, Temperature.class.getSimpleName());
        job.setJarByClass(Temperature.class);
        //为job的输出数据设置key类型
        job.setOutputKeyClass(Text.class);
        //为job的输出数据设置value类型
        job.setOutputValueClass(IntWritable.class);
        //为job设置mapper类
        job.setMapperClass(com.ot.example.map.Temperature.TempMapper.class);
        //为job设置combine类
        //运行简单的聚集方法，最大值，最小值，可以直接使用reduce作为combiner，否则将会出现错误的结果，求平均值不能使用
        //combiner并不能完全取代reduce，但是可以减少mapper和reduce之间的数据传输
        job.setCombinerClass(com.ot.example.map.Temperature.TempReduce.class);
        //为job设置reduce类
        job.setReducerClass(com.ot.example.map.Temperature.TempReduce.class);
        //为map-reduce任务设置InputFormat实现类
        job.setInputFormatClass(TextInputFormat.class);
        //为map-reduce任务设置OutputFormat实现类
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(dst));
        FileOutputFormat.setOutputPath(job, new Path(out));
        job.waitForCompletion(true);
    }
}
    /*
2014010114
2014010216
2014010317
2014010410
2014010506
2012010609
2012010732
2012010812
2012010919
2012011023
2001010116
2001010212
2001010310
2001010411
2001010529
2013010619
2013010722
2013010812
2013010929
2013011023
2008010105
2008010216
2008010337
2008010414
2008010516
2007010619
2007010712
2007010812
2007010999
2007011023
2010010114
2010010216
2010010317
2010010410
2010010506
2015010649
2015010722
2015010812
2015010999
2015011023
     */
//========================输出结果===================================

    /*
Before Mapper: 0, 2014010114======After Mapper:2014, 14
Before Mapper: 11, 2014010216======After Mapper:2014, 16
Before Mapper: 22, 2014010317======After Mapper:2014, 17
Before Mapper: 33, 2014010410======After Mapper:2014, 10
Before Mapper: 44, 2014010506======After Mapper:2014, 6
Before Mapper: 55, 2012010609======After Mapper:2012, 9
Before Mapper: 66, 2012010732======After Mapper:2012, 32
Before Mapper: 77, 2012010812======After Mapper:2012, 12
Before Mapper: 88, 2012010919======After Mapper:2012, 19
Before Mapper: 99, 2012011023======After Mapper:2012, 23
Before Mapper: 110, 2001010116======After Mapper:2001, 16
Before Mapper: 121, 2001010212======After Mapper:2001, 12
Before Mapper: 132, 2001010310======After Mapper:2001, 10
Before Mapper: 143, 2001010411======After Mapper:2001, 11
Before Mapper: 154, 2001010529======After Mapper:2001, 29
Before Mapper: 165, 2013010619======After Mapper:2013, 19
Before Mapper: 176, 2013010722======After Mapper:2013, 22
Before Mapper: 187, 2013010812======After Mapper:2013, 12
Before Mapper: 198, 2013010929======After Mapper:2013, 29
Before Mapper: 209, 2013011023======After Mapper:2013, 23
Before Mapper: 220, 2008010105======After Mapper:2008, 5
Before Mapper: 231, 2008010216======After Mapper:2008, 16
Before Mapper: 242, 2008010337======After Mapper:2008, 37
Before Mapper: 253, 2008010414======After Mapper:2008, 14
Before Mapper: 264, 2008010516======After Mapper:2008, 16
Before Mapper: 275, 2007010619======After Mapper:2007, 19
Before Mapper: 286, 2007010712======After Mapper:2007, 12
Before Mapper: 297, 2007010812======After Mapper:2007, 12
Before Mapper: 308, 2007010999======After Mapper:2007, 99
Before Mapper: 319, 2007011023======After Mapper:2007, 23
Before Mapper: 330, 2010010114======After Mapper:2010, 14
Before Mapper: 341, 2010010216======After Mapper:2010, 16
Before Mapper: 352, 2010010317======After Mapper:2010, 17
Before Mapper: 363, 2010010410======After Mapper:2010, 10
Before Mapper: 374, 2010010506======After Mapper:2010, 6
Before Mapper: 385, 2015010649======After Mapper:2015, 49
Before Mapper: 396, 2015010722======After Mapper:2015, 22
Before Mapper: 407, 2015010812======After Mapper:2015, 12
Before Mapper: 418, 2015010999======After Mapper:2015, 99
Before Mapper: 429, 2015011023======After Mapper:2015, 23


Before Reduce: 2001, 12, 10, 11, 29, 16, ======After Reduce: 2001, 29
Before Reduce: 2007, 23, 19, 12, 12, 99, ======After Reduce: 2007, 99
Before Reduce: 2008, 16, 14, 37, 16, 5, ======After Reduce: 2008, 37
Before Reduce: 2010, 10, 6, 14, 16, 17, ======After Reduce: 2010, 17
Before Reduce: 2012, 19, 12, 32, 9, 23, ======After Reduce: 2012, 32
Before Reduce: 2013, 23, 29, 12, 22, 19, ======After Reduce: 2013, 29
Before Reduce: 2014, 14, 6, 10, 17, 16, ======After Reduce: 2014, 17
Before Reduce: 2015, 23, 49, 22, 12, 99, ======After Reduce: 2015, 99
     */

    /*
Mapper的输入数据(k1,v1)格式是：默认的按行分的键值对<0, 2010012325>，<11, 2012010123>...
Reducer的输入数据格式是：把相同的键合并后的键值对：<2001, [12, 32, 25...]>，<2007, [20, 34, 30...]>...
Reducer的输出数(k3,v3)据格式是：经自己在Reducer中写出的格式：<2001, 32>，<2007, 34>...
     */

//=====================================================================linux日志

/*
[root@idata190 ~]# hadoop jar /root/hadoop1-1.0-SNAPSHOT.jar
2021-01-21 09:17:31,527 INFO client.RMProxy: Connecting to ResourceManager at idata190/192.168.100.190:8032
2021-01-21 09:17:31,975 WARN mapreduce.JobResourceUploader: Hadoop command-line option parsing not performed. Implement the Tool interface and execute your application with ToolRunner to remedy this.
2021-01-21 09:17:32,000 INFO mapreduce.JobResourceUploader: Disabling Erasure Coding for path: /tmp/hadoop-yarn/staging/root/.staging/job_1610958145806_0004
2021-01-21 09:17:32,442 INFO input.FileInputFormat: Total input files to process : 1
2021-01-21 09:17:32,632 INFO mapreduce.JobSubmitter: number of splits:1
2021-01-21 09:17:33,383 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1610958145806_0004
2021-01-21 09:17:33,385 INFO mapreduce.JobSubmitter: Executing with tokens: []
2021-01-21 09:17:33,627 INFO conf.Configuration: resource-types.xml not found
2021-01-21 09:17:33,627 INFO resource.ResourceUtils: Unable to find 'resource-types.xml'.
2021-01-21 09:17:33,717 INFO impl.YarnClientImpl: Submitted application application_1610958145806_0004
2021-01-21 09:17:33,768 INFO mapreduce.Job: The url to track the job: http://idata190:8088/proxy/application_1610958145806_0004/
2021-01-21 09:17:33,769 INFO mapreduce.Job: Running job: job_1610958145806_0004
2021-01-21 09:17:53,994 INFO mapreduce.Job: Job job_1610958145806_0004 running in uber mode : false
2021-01-21 09:17:53,996 INFO mapreduce.Job:  map 0% reduce 0%
2021-01-21 09:18:00,087 INFO mapreduce.Job:  map 100% reduce 0%
2021-01-21 09:18:06,134 INFO mapreduce.Job:  map 100% reduce 100%
2021-01-21 09:18:06,146 INFO mapreduce.Job: Job job_1610958145806_0004 completed successfully
2021-01-21 09:18:06,307 INFO mapreduce.Job: Counters: 53
        File System Counters
                FILE: Number of bytes read=94
                FILE: Number of bytes written=434807
                FILE: Number of read operations=0
                FILE: Number of large read operations=0
                FILE: Number of write operations=0
                HDFS: Number of bytes read=590
                HDFS: Number of bytes written=64
                HDFS: Number of read operations=8
                HDFS: Number of large read operations=0
                HDFS: Number of write operations=2
        Job Counters
                Launched map tasks=1
                Launched reduce tasks=1
                Rack-local map tasks=1
                Total time spent by all maps in occupied slots (ms)=2915
                Total time spent by all reduces in occupied slots (ms)=3178
                Total time spent by all map tasks (ms)=2915
                Total time spent by all reduce tasks (ms)=3178
                Total vcore-milliseconds taken by all map tasks=2915
                Total vcore-milliseconds taken by all reduce tasks=3178
                Total megabyte-milliseconds taken by all map tasks=2984960
                Total megabyte-milliseconds taken by all reduce tasks=3254272
        Map-Reduce Framework
                Map input records=40
                Map output records=40
                Map output bytes=360
                Map output materialized bytes=94
                Input split bytes=112
                Combine input records=40
                Combine output records=8
                Reduce input groups=8
                Reduce shuffle bytes=94
                Reduce input records=8
                Reduce output records=8
                Spilled Records=16
                Shuffled Maps =1
                Failed Shuffles=0
                Merged Map outputs=1
                GC time elapsed (ms)=154
                CPU time spent (ms)=1850
                Physical memory (bytes) snapshot=701796352
                Virtual memory (bytes) snapshot=5963968512
                Total committed heap usage (bytes)=1183842304
                Peak Map Physical memory (bytes)=351985664
                Peak Map Virtual memory (bytes)=2979274752
                Peak Reduce Physical memory (bytes)=349810688
                Peak Reduce Virtual memory (bytes)=2984693760
        Shuffle Errors
                BAD_ID=0
                CONNECTION=0
                IO_ERROR=0
                WRONG_LENGTH=0
                WRONG_MAP=0
                WRONG_REDUCE=0
        File Input Format Counters
                Bytes Read=478
        File Output Format Counters
                Bytes Written=64
[root@idata190 ~]#

 */

//====================================================查看结果===========================================
/*
[root@idata190 ~]# hdfs dfs -ls /test
Found 4 items
-rw-r--r--   3 hello world supergroup       2700 2021-01-20 09:30 /test/1.doc
-rw-r--r--   1 root        supergroup        478 2021-01-20 17:24 /test/hadoop-map.txt
drwxr-xr-x   - root        supergroup          0 2021-01-21 09:18 /test/out
-rw-r--r--   1 root        supergroup        483 2021-01-19 16:16 /test/test1.txt
[root@idata190 ~]# hdfs dfs -ls /test/out
Found 2 items
-rw-r--r--   1 root supergroup          0 2021-01-21 09:18 /test/out/_SUCCESS   表示成功
-rw-r--r--   1 root supergroup         64 2021-01-21 09:18 /test/out/part-r-00000 一个分区00000，多个分区，顺序递增
[root@idata190 ~]# hdfs dfs -cat /test/out/part-r-00000
2001    29
2007    99
2008    37
2010    17
2012    32
2013    29
2014    17
2015    99
[root@idata190 ~]#

 */
