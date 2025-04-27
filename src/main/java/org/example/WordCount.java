package org.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by charming on 2018/4/4.
 */
public class WordCount {

    public static class WordMapper extends Mapper<Object, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        @Override
        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens())
            {
                word.set(itr.nextToken());
                context.write(word, one);
            }
        }
    }

    public static class WordReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values)
            {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration(); //程序运行时参数
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length != 2)
        {
            System.err.println("Usage wordcount <in> <out>");
            System.exit(2);
        }

        // 删除输出目录
        Path outputPath = new Path(otherArgs[1]);
        if (outputPath.getFileSystem(conf).exists(outputPath));
        {
            outputPath.getFileSystem(conf).delete(outputPath, true);
        }

        Job job = Job.getInstance(conf, "word count"); //设置环境参数

        job.setJarByClass(WordCount.class); // 设置整个程序类名

        job.setMapperClass(WordMapper.class); //map函数
//        job.setCombinerClass(WordReduce.class); //combine的实现和reduce一样
        job.setReducerClass(WordReduce.class); //reudce函数

        job.setOutputKeyClass(Text.class); //设置输出key类型
        job.setOutputValueClass(IntWritable.class); //设置输出value类型

        FileInputFormat.addInputPath(job, new Path(otherArgs[0])); // 设置输入文件
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1])); // 设置输出文件

        System.exit(job.waitForCompletion(true)?0:1); // 提交作业
    }
}