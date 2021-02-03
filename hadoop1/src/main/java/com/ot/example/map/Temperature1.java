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
public class Temperature1 {

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
        hadoopConfig.set("fs.defaultFS", "hdfs://192.168.100.19b0:9000");
        hadoopConfig.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        hadoopConfig.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        System.setProperty("HADOOP USER_NAME", "root");
        Job job = Job.getInstance(hadoopConfig, Temperature1.class.getSimpleName());
        job.setJarByClass(Temperature1.class);
        //为job的输出数据设置key类型
        job.setOutputKeyClass(Text.class);
        //为job的输出数据设置value类型
        job.setOutputValueClass(IntWritable.class);
        //为job设置mapper类
        job.setMapperClass(Temperature1.TempMapper.class);
        //为job设置combine类
        //运行简单的聚集方法，最大值，最小值，可以直接使用reduce作为combiner，否则将会出现错误的结果，求平均值不能使用
        //combiner并不能完全取代reduce，但是可以减少mapper和reduce之间的数据传输
        job.setCombinerClass(Temperature1.TempReduce.class);
        //为job设置reduce类
        job.setReducerClass(Temperature1.TempReduce.class);
        //为map-reduce任务设置InputFormat实现类
        job.setInputFormatClass(TextInputFormat.class);
        //为map-reduce任务设置OutputFormat实现类
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(dst));
        FileOutputFormat.setOutputPath(job, new Path(out));
        job.waitForCompletion(true);
    }
}
