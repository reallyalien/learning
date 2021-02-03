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
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetOutputFormat;
import org.apache.parquet.hadoop.example.GroupWriteSupport;

import java.io.IOException;
import java.util.StringTokenizer;

public class ParquetNewMR {

    public static class WordCountMap extends
            Mapper<LongWritable, Text, Text, IntWritable> {

        private final IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String line = value.toString();
            StringTokenizer token = new StringTokenizer(line);
            while (token.hasMoreTokens()) {
                word.set(token.nextToken());
                context.write(word, one);
            }
        }
    }

    public static class WordCountReduce extends
            Reducer<Text, IntWritable, Void, Group> {
        private SimpleGroupFactory factory;

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            Group group = factory.newGroup()
                    .append("name", key.toString())
                    .append("id", sum);
            context.write(null, group);
        }

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            factory = new SimpleGroupFactory(GroupWriteSupport.getSchema(context.getConfiguration()));

        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        String writeSchema = "message example {\n" +
                "required binary name;\n" +
                "required int32 id;\n" +
                "}";
        conf.set("parquet.example.schema", writeSchema);

        Job job = Job.getInstance(conf);
        job.setJarByClass(ParquetNewMR.class);
        job.setJobName("parquet");

        String in = "hdfs://192.168.100.190:9000/test/hive-parquet.txt";
        String out = "hdfs://192.168.100.190:9000/test/out1";

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputValueClass(Group.class);

        job.setMapperClass(WordCountMap.class);
        job.setReducerClass(WordCountReduce.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(ParquetOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(in));
        ParquetOutputFormat.setOutputPath(job, new Path(out));
        ParquetOutputFormat.setWriteSupportClass(job, GroupWriteSupport.class);

        job.waitForCompletion(true);
    }
}
