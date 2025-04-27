package org.example;

import java.io.IOException;
import java.net.URI;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;


public class test {
    public static void main(String[] args) throws IOException, InterruptedException {
        initLogRecord.initLog();
        test t = new test();
//        t.download();
        t.upload();
        System.out.println("Success");
    }
//tar -zxvf apache-hive-2.3.3-bin.tar.gz -C
    public void download() throws IOException, InterruptedException {
        Configuration conf = new Configuration();
        URI uri = URI.create("hdfs://192.168.248.128:9000//");
        FileSystem fs = FileSystem.get(uri,conf,"root");
        fs.copyToLocalFile(new Path("/input/data.txt" ),new Path("D:/A.txt"));
        fs.close();
    }

    public void upload() throws IOException, InterruptedException {
        Configuration conf = new Configuration();
        URI uri = URI.create("hdfs://192.168.207.128:9000//");
        FileSystem fs = FileSystem.get(uri,conf,"root");
        fs.copyFromLocalFile(new Path("C:\\Users\\86195\\Desktop\\mysql-connector-java-8.0.18.jar"),new Path("/mydir/mysql-connector-java-8.0.18.jar"));
        fs.close();
    }
}
